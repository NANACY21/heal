package com.personal.service;

import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;

import java.util.List;

/**
 * @author 李箎
 */
public interface PositionService {

    /**
     * 添加/编辑职位
     *
     * @param position
     * @return
     */
    String savePosition(Position position);

    /**
     * 删除职位 已发布的职位、有申请的职位不能删除（需要先撤回）
     *
     * @param positionIds 一些职位id
     * @return
     */
    String delPosition(long[] positionIds);

    /**
     * 发布职位
     *
     * @param id 职位id
     * @return
     */
    String releasePosition(long id);

    /**
     * 撤回职位
     *
     * @param id
     * @return
     */
    String withdrawPosition(long id);

    /**
     * 某公司的招聘职位列表 包括已/未发布的
     *
     * @param companyId
     * @return
     */
    List<Position> getPositionList(Long companyId);

    /**
     * 已发布职位列表
     *
     * @return
     */
    List<ReleasePosition> getReleasePositionList(int status, long rowNum);

    /**
     * 通过id查找职位
     * @param id
     * @return
     */
    Position getPositionById(long id);
}
