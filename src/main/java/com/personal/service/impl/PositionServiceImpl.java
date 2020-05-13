package com.personal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.ESService.ElasticSearchUtil;
import com.personal.mapper.PositionMapper;
import com.personal.mapper.UsersMapper;
import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import com.personal.redisService.tool.RedisUtil;
import com.personal.service.PositionService;
import com.personal.util.ConstPool;
import com.personal.util.ObjectPool;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper mapper;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ElasticSearchUtil searchUtil;
    @Autowired
    private Util util;
    /**
     * 添加/编辑职位
     *
     * @param position
     * @return
     */
    @Override
    public String savePosition(Position position) {
        //新增的职位未发布
        position.setStatus(0);
        //添加职位
        if (position.getId() == null) {
            if (mapper.getPositionByName(position.getName(), position.getCompanyId()).size() > 0) {
                position.setName(null);
                return "职位已存在，新增职位失败";
            }
            //设置该职位发布时间！！！
            position.setReleaseTime(null);
            //insert后自增的MySQL主键id 可以拿到，在该方法的参数里！！！
            int i = mapper.insertSelective(position);
            if (i > 0) {
                return ConstPool.ADD_POSITION_SUCCESS;
            }
            position.setName(null);
            return ConstPool.ADD_POSITION_FAIL;
        }
        //编辑职位并保存
        else if (position.getId() != null) {
            int i = mapper.updateByPrimaryKeySelective(position);
            if (i > 0) {
                return "职位修改保存成功";
            }
            position.setName(null);
            return "职位修改保存失败";
        }
        return "失败";
    }

    /**
     * 删除职位 已发布的职位、有申请的职位不能删除（需要先撤回）
     *
     * @param positionIds 一些职位id
     * @return
     */
    @Transactional
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delPosition(long[] positionIds) {
        //存放要删除的职位的名称
        String[] positionName = new String[positionIds.length];
        long[] positionId = new long[positionIds.length];

        for (int i = 0; i < positionIds.length; i++) {
            Position position = mapper.selectByPrimaryKey(positionIds[i]);
            //要删的职位名称
            positionName[i] = position.getName();
            positionId[i] = position.getCompanyId();
            //职位不存在、已发布、等
            if (position == null || position.getStatus() == 1) {
                return "选中职位全删除失败";
            }
        }
        for (long id : positionIds) {
            int i = mapper.deleteByPrimaryKey(id);
            if (i <= 0) {
                //有一条删除出错则事务回滚 有时冥思苦想也想不出来是因为方向不对
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "选中职位全删除失败";
            }
        }
        //同步删除ES中的数据 没用动态代理
        for (int i = 0; i < positionName.length; i++) {
            String ESid = searchUtil.getPositionByName(positionName[i], positionId[i]);
            searchUtil.delPosition(ESid, ConstPool.INDEX_NAME);
        }
        return "选中职位删除成功";
    }

    /**
     * 发布/撤回职位
     * 撤回职位可以随时自由撤回
     * 该方法有前置方法
     *
     * @param map 职位id 要改成的职位状态
     * @return
     */
    @Override
    public String changePositionStatus(Map<String, Object> map) {
        //要修改的职位
        Position position = ObjectPool.getPosition();
        if (position == null) {
            return ConstPool.POSITION_NOT_EXIST;
        }
        //原来的职位状态
        int originalStatus = position.getStatus();
        //要改成的职位状态
        int status = Integer.parseInt(map.get("status").toString());
        if (originalStatus == status) {
            return ConstPool.POSITION_RELEASED;
        }
        if (status == 1 && originalStatus == 0) {
            position.setReleaseTime(Util.getTime());
        }
        int i = 0;
        if ((status == 1 && originalStatus == 0) || (status == 0 && originalStatus == 1)) {
            position.setStatus(status);
            i = mapper.updateByPrimaryKeySelective(position);
        }
        if (i > 0) {
            return status == 1 ? ConstPool.POSITION_RELEASE_SUCCESS : ConstPool.POSITION_WITHDRAW_SUCCESS;
        }
        //更新对象队列数据
        ObjectPool.setPosition(position);
        return ConstPool.POSITION_NOT_EXIST;
    }

    /**
     * 查询某公司的招聘职位
     *
     * @param map companyId，当前页，页大小，职位状态
     * @return
     */
    @Override
    public List<Position> getPositionList(Map<String, Object> map) {
        Map<String, Object> temp = new HashMap<>();
        long companyId = Long.parseLong(map.get("id").toString());
        temp.put("companyId", companyId);
        if (map.get("currentPage") != null && map.get("pageSize") != null) {
            int currentPage = Integer.parseInt(map.get("currentPage").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            temp.put("beginRowIndex", Util.getLimitVariable(currentPage, pageSize).get("beginRowIndex"));
            temp.put("rowNum", Util.getLimitVariable(currentPage, pageSize).get("rowNum"));
        }
        Object status = map.get("status");
        if (
                status != null &&
                status.toString().length() != 0
        ) {
            temp.put("status", Integer.parseInt(status.toString()));
        }
        return mapper.selectByCompanyId(temp);
    }

    /**
     * 查询某个人在某公司投递的职位
     *
     * @param userId
     * @param companyId
     * @return
     */
    @Override
    public List<Position> getPositionNameList(Long userId, Long companyId) {
        return mapper.getPositionNameList(userId, companyId);
    }

    /**
     * 已发布职位列表
     *
     * @param queryCondition 查询条件
     * @return
     */
    @Override
    public List<ReleasePosition> getReleasePositionList(Map<String, Object> queryCondition) {
        List<String> cityList = (List<String>) queryCondition.get("city");
        if (cityList.size() == 0 || "全国".equals(cityList.get(0))) {
            queryCondition.put("city", null);
        }
        List<String> workExpList = (List<String>) queryCondition.get("workExp");
        if (workExpList.size() == 0 || "不限".equals(workExpList.get(0))) {
            queryCondition.put("workExp", null);
        }
        List<String> eduBgList = (List<String>) queryCondition.get("eduBg");
        if (eduBgList.size() == 0 || "不限".equals(eduBgList.get(0))) {
            queryCondition.put("eduBg", null);
        }

        //！！！
        if (queryCondition.get("currentPage") != null && queryCondition.get("pageSize") != null) {
            int currentPage = Integer.parseInt(queryCondition.get("currentPage").toString());
            int pageSize = Integer.parseInt(queryCondition.get("pageSize").toString());
            queryCondition.put("beginRowIndex", (currentPage - 1) * pageSize);
            queryCondition.put("rowNum", pageSize);
        }
        List<ReleasePosition> list = mapper.selectByStatus(queryCondition);
        return util.getCompanyInfo(list);
    }

    /**
     * 通过id查找职位
     *
     * @param id
     * @return
     */
    @Override
    public Position getPositionById(long id) {
        Position position = mapper.selectByPrimaryKey(id);
        System.out.println(position);
        return position;
    }

    /**
     * 收藏该职位/取消收藏该职位
     *
     * @param map
     * @return
     */
    @Override
    public String collectPosition(Map<String, Object> map) {
        String username = map.get("username").toString();
        String positionId = map.get("positionId").toString();
        List<String> listVal = redisUtil.getListVal(username + ConstPool.COLLECT_POSITION);
        if (listVal.contains(positionId)) {
            redisUtil.delete(username + ConstPool.COLLECT_POSITION, positionId);
            return "已取消收藏";
        }
        redisUtil.insert(username + ConstPool.COLLECT_POSITION, positionId);
        return "已收藏";
    }

    /**
     * 收藏的职位列表
     *
     * @param username
     * @return
     */
    @Override
    public List<ReleasePosition> collectPositionList(String username) {
        if (usersMapper.selectByUsername(username).getUserType() != 1) {
            return new ArrayList<>();
        }
        //读redis
        List<String> positionIdList = redisUtil.getListVal(username + ConstPool.COLLECT_POSITION);
        if (positionIdList == null || positionIdList.size() == 0) {
            return new ArrayList<>();
        }
        List<ReleasePosition> list = mapper.collectPositionList(positionIdList);
        return util.getCompanyInfo(list);
    }

    /**
     * 通过我的用户id和公司id 我投递到该公司的所有职位均 标记：被查看
     *
     * @param userId
     * @param companyId
     * @param postStatus 要改成的状态
     * @return
     */
    @Override
    public int changePostStatus(Long userId, Long companyId, int postStatus) {
        if (postStatus == -2) {
            return mapper.changePostStatus2(userId, companyId, postStatus);
        } else if (postStatus > 0) {
            return mapper.changePostStatus(userId, companyId, postStatus);
        } else {
            //操作失败
            return -18;
        }
    }

    /**
     * 从投递箱移除一个职位
     *
     * @param map key 值 key 数组
     * @return
     */
    @Override
    public String deletePost(Map<String, Object> map) {
        Long userId = Long.valueOf(map.get("userId").toString());
        List<Long> positionIds = (List<Long>) map.get("positionIds");
        int i = mapper.deletePost(map);
        return i > 0 ? "ok" : "error";
    }
}
