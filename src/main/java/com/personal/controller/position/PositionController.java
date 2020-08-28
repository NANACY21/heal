package com.personal.controller.position;

import com.personal.ESService.ElasticSearchUtil;
import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import com.personal.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 职位
 *
 * @author 李箎
 */
@Controller
public class PositionController {

    @Autowired
    private PositionService service;
    @Autowired
    private ElasticSearchUtil searchUtil;

    /**
     * 企业版 - 添加/编辑职位
     *
     * @param position
     * @return
     */
    @RequestMapping("/savePosition")
    @ResponseBody
    public String savePosition(@RequestBody Position position) {
        System.out.println(position);
        return service.savePosition(position);
    }

    /**
     * 企业版 - 删除职位
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
     * 企业版 - 发布/撤回职位
     *
     * @param map 职位id 要改成的职位状态
     * @return
     */
    @RequestMapping("/changePositionStatus")
    @ResponseBody
    public String changePositionStatus(@RequestBody Map<String, Object> map) {
        return service.changePositionStatus(map);
    }

    /**
     * 企业版 - 某公司的招聘职位列表
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    @RequestMapping("/getPositionList")
    @ResponseBody
    public List<Position> getPositionList(@RequestBody Map<String, Object> map) {
        return service.getPositionList(map);
    }

    /**
     * 企业版 - 某公司的招聘职位列表长度
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    @RequestMapping("/getPositionListLength")
    @ResponseBody
    public int getPositionListLength(@RequestBody Map<String, Object> map) {
        return service.getPositionList(map).size();
    }

    /**
     * 在 企业版 - 职位列表 中 搜索职位
     *
     * @param map 搜索内容，公司id
     * @return
     */
    @RequestMapping("/searchPosition")
    @ResponseBody
    public List<Position> searchPosition(@RequestBody Map<String, Object> map) {
        return searchUtil.getPositionList(map);
    }

    /**
     * 查询某个人投递到某公司的职位
     *
     * @param map userId，companyId
     * @return
     */
    @RequestMapping("/getPositionNameList")
    @ResponseBody
    public List<Position> getPositionNameList(@RequestBody Map<String, Object> map) {
        return service.getPositionNameList(map);
    }

    /**
     * 某公司的职位名称列表
     *
     * @param map companyId
     * @return
     */
    @RequestMapping("/positionNameList")
    @ResponseBody
    public List<String> positionNameList(@RequestBody Map<String, Object> map) {
        map.remove("status");
        map.remove("currentPage");
        map.remove("pageSize");
        List<String> nameList = new ArrayList<>();
        List<Position> positionList = service.getPositionList(map);
        for (int i = 0; i < positionList.size(); i++) {
            nameList.add(positionList.get(i).getName());
        }
        return nameList;
    }


    /**
     * 获得已发布的职位列表（首页推荐的职位）
     *
     * @param map 查询条件 Map<String, List<String>>
     * @return
     */
    @RequestMapping("/getReleasePositionList")
    @ResponseBody
    public List<ReleasePosition> getReleasePositionList(@RequestBody Map<String, Object> map) {
        return service.getReleasePositionList(map);
    }

    /**
     * 获得已发布的职位列表（首页推荐的职位）总长度
     *
     * @param map 查询条件
     * @return
     */
    @RequestMapping("/getReleasePositionListLength")
    @ResponseBody
    public int getReleasePositionListLength(@RequestBody Map<String, Object> map) {
        return service.getReleasePositionList(map).size();
    }

    /**
     * 通过id查找职位
     *
     * @param id 职位id
     * @return
     */
    @RequestMapping("/getPositionById")
    @ResponseBody
    public Position getPositionById(@RequestBody long id) {
        return service.getPositionById(id);
    }


    //收藏相关


    /**
     * 收藏/取消收藏该职位
     *
     * @param map 用户名，职位id
     * @return
     */
    @RequestMapping("/collectPosition")
    @ResponseBody
    public String collectPosition(@RequestBody Map<String, Object> map) {
        return service.collectPosition(map);
    }

    /**
     * 用户收藏的职位列表
     *
     * @param username
     * @return
     */
    @RequestMapping("/collectPositionList")
    @ResponseBody
    public List<ReleasePosition> collectPositionList(@RequestBody String username) {
        return service.collectPositionList(username);
    }
}
