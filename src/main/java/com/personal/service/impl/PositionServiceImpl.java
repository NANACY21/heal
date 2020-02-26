package com.personal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.mapper.PositionMapper;
import com.personal.mapper.UsersMapper;
import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import com.personal.service.PositionService;
import com.personal.util.ConstPool;
import com.personal.util.ObjectPool;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.util.List;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper mapper;

    /**
     * 添加/编辑职位
     *
     * @param position
     * @return
     */
    @Override
    public String savePosition(Position position) {
        //添加职位
        if (position.getId() == null) {
            //设置该职位发布时间
            position.setReleaseTime(Util.getTime());
            int i = mapper.insertSelective(position);
            if (i > 0) {
                return ConstPool.ADD_POSITION_SUCCESS;
            }
            return ConstPool.ADD_POSITION_FAIL;
        }
        //编辑职位并保存
        else if (position.getId() != null) {
            int i = mapper.updateByPrimaryKeySelective(position);
            if (i > 0) {
                return "职位修改保存成功";
            }
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
        for (int i = 0; i < positionIds.length; i++) {
            Position position = mapper.selectByPrimaryKey(positionIds[i]);
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
        return "选中职位删除成功";
    }

    /**
     * 发布职位
     * 该方法有前置方法
     * @param id 职位id
     * @return
     */
    @Override
    public String releasePosition(long id) {
        Position position = ObjectPool.getPosition();
        if (position == null) {
            return ConstPool.POSITION_NOT_EXIST;
        }
        if (position.getStatus() == 1) {
            return ConstPool.POSITION_RELEASED;
        } else if (position.getStatus() == 0) {
            position.setStatus(1);
            position.setReleaseTime(Util.getTime());
            int i = mapper.updateByPrimaryKeySelective(position);
            if (i > 0) {
                return ConstPool.POSITION_RELEASE_SUCCESS;
            }
        }
        return ConstPool.POSITION_NOT_EXIST;
    }

    /**
     * 撤回职位
     * 撤回职位可以随时自由撤回
     * 该方法有前置方法
     * @param id 职位id
     * @return
     */
    @Override
    public String withdrawPosition(long id) {
        //消费者
        Position position = ObjectPool.getPosition();
        if (position == null) {
            return ConstPool.POSITION_NOT_EXIST;
        }
        if (position.getStatus() == 0) {
            return ConstPool.POSITION_RELEASED;
        } else if (position.getStatus() == 1) {
            position.setStatus(0);
            int i = mapper.updateByPrimaryKeySelective(position);
            if (i > 0) {
                return ConstPool.POSITION_WITHDRAW_SUCCESS;
            }
        }
        return ConstPool.POSITION_NOT_EXIST;
    }

    /**
     * 查询某公司的招聘职位
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Position> getPositionList(Long companyId) {
        return mapper.selectByCompanyId(companyId);
    }

    /**
     * 已发布职位列表
     *
     * @param status
     * @return
     */
    @Override
    public List<ReleasePosition> getReleasePositionList(int status, long rowNum) {
        List<ReleasePosition> list = mapper.selectByStatus(status, rowNum);
        //给公司名称、公司信息赋上值
        for (ReleasePosition rp : list) {
            String jsonStr = Util.readJsonFileTool(new File(ConstPool.JSON_PATH, rp.getCompanyId() + ".json"));
            //json串->对象
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String companyName = jsonObject.get("name").toString();
            rp.setCompanyName(companyName);
            JSONObject introduction = jsonObject.getJSONObject("introduction");
            String summary = introduction.get("summary").toString();
            rp.setCompanyInfo(summary);
        }
        return list;
    }

    /**
     * 通过id查找职位
     *
     * @param id
     * @return
     */
    @Override
    public Position getPositionById(long id) {
        return mapper.selectByPrimaryKey(id);
    }
}
