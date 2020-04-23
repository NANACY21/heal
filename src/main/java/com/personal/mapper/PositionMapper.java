package com.personal.mapper;

import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;

import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
public interface PositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Long id);

    /**
     * 查询某公司的招聘职位
     * @param map companyId，beginRowIndex，页大小
     * @return
     */
    List<Position> selectByCompanyId(Map<String, Object> map);

    List<Position> getPositionNameList(Long userId, Long companyId);

    /**
     * 已发布的职位列表，包含职位信息、公司信息、发布人信息
     * @param queryCondition
     * @return
     */
    List<ReleasePosition> selectByStatus(Map<String, Object> queryCondition);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<ReleasePosition> collectPositionList(List<String> ids);

    /**改求职者投递职位的投递状态
     * 通过我的用户id和公司id 我投递到该公司的所有职位均 标记：被查看
     *
     * @param userId
     * @param companyId
     * @param postStatus 要改成的状态
     * @return
     */
    int changePostStatus(Long userId, Long companyId, int postStatus);

    /**改求职者投递职位的投递状态
     * 要改成的投递状态为-2时 hr认为不合适
     * @param userId
     * @param companyId
     * @param postStatus 要改成的状态
     * @return
     */
    int changePostStatus2(Long userId, Long companyId, int postStatus);

    /**
     * 投递箱移除一个职位
     * @param map userId 值 positionIds 职位id数组
     * @return
     */
    int deletePost(Map<String, Object> map);

    /**
     * 不可以添加重复的职位
     * @param name
     * @param companyId
     * @return
     */
    List<Position> getPositionByName(String name, long companyId);
}