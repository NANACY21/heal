package com.personal.service;

import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
public interface PositionService {

    /**
     * 企业版 - 添加/编辑职位
     *
     * @param position
     * @return
     */
    String savePosition(Position position);

    /**
     * 企业版 - 删除职位
     * 已发布的职位、有申请的职位不能删除（需要先撤回）
     *
     * @param positionIds 一些职位id
     * @return
     */
    String delPosition(long[] positionIds);

    /**
     * 企业版 - 发布/撤回职位
     *
     * @param map 职位id 要改成的职位状态
     * @return
     */
    String changePositionStatus(Map<String, Object> map);

    /**
     * 企业版 - 某公司的招聘职位列表
     * 包括已/未发布的
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    List<Position> getPositionList(Map<String, Object> map);

    /**
     * 查询某个人投递到某公司的职位
     * @param map userId，companyId
     * @return
     */
    List<Position> getPositionNameList(Map<String, Object> map);

    /**
     * 获得已发布的职位列表（首页推荐的职位）
     * @param queryCondition 查询条件
     * @return
     */
    List<ReleasePosition> getReleasePositionList(Map<String, Object> queryCondition);

    /**
     * 通过id查找职位
     * @param id
     * @return
     */
    Position getPositionById(long id);

    /**
     * 收藏/取消收藏该职位
     *
     * @param map 用户名，职位id
     * @return
     */
    String collectPosition(Map<String, Object> map);

    /**
     * 用户收藏的职位列表
     * @param username
     * @return
     */
    List<ReleasePosition> collectPositionList(String username);

    /**
     * 通过我的用户id和公司id 我投递到该公司的所有职位均 标记：被查看
     *
     * @param map userId companyId 要改成的状态
     * @return
     */
    int changePostStatus(Map<String, Object> map);

    /**
     * 从投递箱移除一个职位
     * @param map
     * @return
     */
    String deletePost(Map<String, Object> map);
}
