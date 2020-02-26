package com.personal.controller;

import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import com.personal.service.PositionService;
import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李箎
 */
@Controller
public class PositionController {
    @Autowired
    private PositionService service;

    /**
     * 企业版-添加/编辑职位
     *
     * @param position
     * @return
     */
    @RequestMapping("/savePosition")
    @ResponseBody
    public String savePosition(@RequestBody Position position) {
        System.out.println(position);
        //新增的职位未发布
        position.setStatus(0);
        return service.savePosition(position);
    }

    /**
     * 企业版-删除职位
     *
     * @param positionIds 前端提交的一些职位id
     * @return
     */
    @RequestMapping("/delPosition")
    @ResponseBody
    public String delPosition(@RequestBody long[] positionIds) {
        return service.delPosition(positionIds);
    }

    /**
     * 发布职位
     * @param id
     * @return
     */
    @RequestMapping("/releasePosition")
    @ResponseBody
    public String releasePosition(@RequestBody long id) {
        return service.releasePosition(id);
    }

    /**
     * 撤回职位
     *
     * @param id
     * @return
     */
    @RequestMapping("/withdrawPosition")
    @ResponseBody
    public String withdrawPosition(@RequestBody long id) {
        return service.withdrawPosition(id);
    }

    /**
     * 职位列表
     * @param companyId
     * @return
     */
    @RequestMapping("/getPositionList")
    @ResponseBody
    public List<Position> getPositionList(@RequestBody long companyId) {
        return service.getPositionList(companyId);
    }


    /**
     *
     * @param rowNum 查询条数
     * @return
     */
    @RequestMapping("/getReleasePositionList")
    @ResponseBody
    public List<ReleasePosition> getReleasePositionList(@RequestBody long rowNum) {
        return service.getReleasePositionList(1, rowNum);
    }
}
