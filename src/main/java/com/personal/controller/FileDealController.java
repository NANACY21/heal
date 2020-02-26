package com.personal.controller;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.SocketException;
import java.util.Map;

/**
 * @author 李箎
 */
@Controller
public class FileDealController {

    public static final String HEADPHOTO = "headphoto";
    public static final String RESUMEPHOTO = "resumephoto";
    public static final String UPLOAD_FAIL = "上传失败";
    public static final String UPLOAD_SUCCESS = "上传成功";
    public static final String LOAD_PHOTO_FAIL = "加载照片失败";

    /**
     * 头像上传 / 简历照片上传
     *
     * 上传图片/文本使用的io流不同，难怪上传的图片打不开
     * 前端el-upload :data 传到这里不加@RequestBody，加了报415
     * @param map
     * @param file
     * @return
     * @throws SocketException
     * @throws IOException
     */
    @RequestMapping("/uploadPhoto")
    @ResponseBody
    public String uploadPhoto(@RequestParam Map<String, Object> map, MultipartFile file) throws SocketException, IOException {
        String path;
        String username = map.get("username").toString();
        String reason = map.get("reason").toString();
        if (HEADPHOTO.equals(reason)) {
            path = ConstPool.HEAD_PHOTO_SAVE_PATH;
        } else if (RESUMEPHOTO.equals(reason)) {
            path = ConstPool.RESUME_PHOTO_SAVE_PATH;
        } else {
            return UPLOAD_FAIL;
        }
        if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().length() == 0) {
            return UPLOAD_FAIL;
        }
        //获取上传的文件名
        String fileName = file.getOriginalFilename();
        //新的文件名
        String newFileName = username + fileName.substring(fileName.lastIndexOf("."));
        File f = new File(path + newFileName);


        //原来有lichil.jpg，现在更换为lichil.png，原来的jpg删除
        //指向目录
        File dir = new File(path);
        //该目录里所有项
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //pictureName：lichil.jpg
                String pictureName = files[i].getName();
                if (pictureName.substring(0, pictureName.lastIndexOf(".")).equals(username)) {
                    files[i].delete();
                }
            }
        }




        if (f.exists()) {
            f.delete();
            f = new File(path + newFileName);
        }
        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
            return UPLOAD_FAIL;
        }
        return UPLOAD_SUCCESS;
    }

    /**
     * 有头像则加载头像 / 简历有照片则加载简历照片
     *
     * @param map 用户名
     * @return
     */
    @RequestMapping("/loadPhoto")
    @ResponseBody
    public String loadPhoto(@RequestBody Map<String, Object> map) throws IOException {
        String path;
        String username = map.get("username").toString();
        String reason = map.get("reason").toString();
        if (HEADPHOTO.equals(reason)) {
            path = ConstPool.HEAD_PHOTO_SAVE_PATH;
        } else if (RESUMEPHOTO.equals(reason)) {
            path = ConstPool.RESUME_PHOTO_SAVE_PATH;
        } else {
            return LOAD_PHOTO_FAIL;
        }
        //指向目录
        File dir = new File(path);
        //该目录里所有项
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //pictureName：lichil.jpg
                String pictureName = files[i].getName();
                if (pictureName.substring(0, pictureName.lastIndexOf(".")).equals(username)) {
                    return pictureName;
                }
            }
        }
        return LOAD_PHOTO_FAIL;
    }

    /**
     * 创建空JSON文件
     *
     * @param companyId
     * @return
     */
    @RequestMapping("/createJSONFile")
    @ResponseBody
    public String createJSONFile(@RequestBody long companyId) {
        File file = new File(ConstPool.JSON_PATH, companyId + ".json");
        try {
            if (file.exists()) {
                return "文件已存在，无需再新建";
            }
            file.createNewFile();
            return "成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
    }

    /**
     * 读取json文件内容，返回json串
     *
     * @param companyId
     * @return
     */
    @RequestMapping("/readJSONFile")
    @ResponseBody
    public String readJSONFile(@RequestBody long companyId) {
        return Util.readJsonFileTool(new File(ConstPool.JSON_PATH, companyId + ".json"));
    }
}
