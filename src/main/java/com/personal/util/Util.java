package com.personal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.pojo.Position;
import com.personal.pojo.Users;
import com.personal.pojo.msg.Message;
import com.personal.pojo.web.ReleasePosition;
import com.personal.service.UsersService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 李箎
 */
@Component
public class Util {
    @Autowired
    private UsersService service;
    /**
     * 实际开发中表的数量很大->需要分表->就不能使用MySQL主键自增
     *
     * @return 线程安全的全局唯一id
     */
    public static String getGlobalUniqueId() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(5);
        //5位随机数
        int num = 5;
        for (int i = 0; i < num; i++) {
            sb.append(String.valueOf(r.nextInt(10)));
        }
        //14位
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmhhss");
        //19位
        return sdf.format(new Date()) + sb.toString();
    }

    public static long getTimeStamp(int bit) {
        if (10 == bit) {
            // 10位数的时间戳
            return System.currentTimeMillis() / 1000;
        } else if (13 == bit) {
            //13位数的时间戳
            return System.currentTimeMillis();
        } else {
            //随机数6位
            return (int) ((Math.random() * 9 + 1) * 100000);
        }
    }

    /**
     * @param date
     * @param min
     * @return date 时间之后 min 分钟的时间
     */
    public static String getDate(Date date, int min) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date afterDate = new Date(date.getTime() + 60000 * min);
        return sdf.format(afterDate);
    }

    /**
     * @return 当前时间
     */
    public static String getTime() {
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 字符串日期转Date
     *
     * @param date
     * @return
     */
    public static Date StringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 相差月数、年数
     *
     * @param from
     * @param to
     * @param flag "y"-年数 "m"-月数
     * @return
     */
    public static int differDate(String from, String to, String flag) {
        Calendar begin = Calendar.getInstance();
        begin.setTime(Util.StringToDate(from));
        Calendar end = Calendar.getInstance();
        end.setTime(Util.StringToDate(to));
        int fromYear = begin.get(Calendar.YEAR);
        int fromMonth = begin.get(Calendar.MONTH);
        int toYear = end.get(Calendar.YEAR);
        int toMonth = end.get(Calendar.MONTH);
        int year = toYear - fromYear;
        if ("y".equals(flag)) {
            return year;
        } else if ("m".equals(flag)) {
            return toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        }
        return 0;
    }

    /**
     * 复制文件
     *
     * @param oldPathName d:a.txt
     * @param newPathName d:a.txt
     */
    public static void fileCopy(String oldPathName, String newPathName) throws IOException {
        File oldPath = new File(oldPathName);
        File newPath = new File(newPathName);
        if (!newPath.exists()) {
            Files.copy(oldPath.toPath(), newPath.toPath());
        } else {
            newPath.delete();
            Files.copy(oldPath.toPath(), newPath.toPath());
        }
    }

    /**
     * 读取json文件内容，返回json串
     *
     * @param jsonFile json文件指针
     * @return
     */
    public static String readJsonFileTool(File jsonFile) {
        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 对象->json串
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json->对象
     *
     * @param object 要转成的对象
     * @param json   json串
     * @return
     */
    public static Object jsonToObject(Object object, String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //还有一种方法 json->对象 JSON.parseObject("")
            return object = mapper.readValue(json, object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 关键！！！
     * 聊天 消息归类
     * 用考虑每一条消息的父消息 消息对象加这一属性？？
     *
     * @param msgList  所有发给我的、我发的消息 和我相关的消息 的列表
     * @param userId 我的消息 我的19位用户号
     * @return key 用户名
     */
    public Map<String, List<Message>> msgSortOut(List<Message> msgList, String userId) {
        if (msgList.size() == 0) {
            return new HashMap<>();
        }
        Map<String, List<Message>> result = new HashMap<>(100);
        //遍历和我有关的消息列表
        for (int i = 0; i < msgList.size(); i++) {
            //一条消息
            Message message = msgList.get(i);
            //谁发的消息
            String fromId = message.getFromId();
            //发给谁了
            String toId = message.getToId();
            //不是我发的 fromId不是我
            if (!fromId.equals(userId)) {
                if (result.containsKey(fromId)) {
                    result.get(fromId).add(message);
                } else {
                    List<Message> resultItem = new ArrayList<>();
                    resultItem.add(message);
                    result.put(fromId, resultItem);
                }
            }
            //是我发的 关键！！！
            else {
                if (result.containsKey(toId)) {
                    //关键！！！
                    result.get(toId).add(message);
                } else {
                    List<Message> resultItem = new ArrayList<>();
                    resultItem.add(message);
                    result.put(toId, resultItem);
                }
            }
        }
        //至此result已ok key是19位用户号 现在替换所有的key为用户名
        // https://blog.csdn.net/dustin_cds/article/details/79676286
        Map<String, List<Message>> newResult = new HashMap<>(100);
        Iterator<Map.Entry<String, List<Message>>> iterator = result.entrySet().iterator();
        Map.Entry<String, List<Message>> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            // 往newResult中放入新的Entry
            String username = service.getUsernameByUserId(entry.getKey());
            newResult.put(username, entry.getValue());
            // 删除老的Entry
            iterator.remove();
        }
        return newResult;
    }

    /**时间复杂度很高，垃圾写法
     * "转码" 消息列表 from/to 19位 转成用户名
     * 这波操作就是时间换空间
     * @param msgList 19位用户号 19位用户号
     * @return
     */
    public List<Message> convertToUsername(List<Message> msgList) {
        for (int i = 0; i < msgList.size(); i++) {
            Message message = msgList.get(i);
            message.setFrom(service.getUsernameByUserId(message.getFrom()));
            message.setTo(service.getUsernameByUserId(message.getTo()));
        }
        return msgList;
    }

    /**
     * 职位对象->map
     * 几乎所有属性
     *
     * @param position
     * @return
     */
    public static Map<String, Object> pojoToMap(Position position) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", position.getName());
        map.put("detail", position.getDetail());
        map.put("city", position.getCity());
        map.put("needNum", position.getNeedNum());
        map.put("workExp", position.getWorkExp());
        map.put("eduBg", position.getEduBg());
        map.put("salary", position.getSalary());
        map.put("salaryFloat", position.getSalaryFloat());
        map.put("worktype", position.getWorktype());
        map.put("faceto", position.getFaceto());
        return map;
    }

    /**
     * 获得 MySQL limit的2个值
     *
     * @param currentPage 当前页
     * @param pageSize    页大小
     * @return
     */
    public static Map<String, Object> getLimitVariable(int currentPage, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("beginRowIndex", (currentPage - 1) * pageSize);
        map.put("rowNum", pageSize);
        return map;
    }

    /**
     * 将png格式图片转换成jpg格式的图片
     *
     * @param file png文件
     */
    public static void pngToJpg(File file) {
        BufferedImage bufferedImage;
        //png图片的完整路径
        String absolutePath = file.getAbsolutePath();
        try {
            //read image file
            bufferedImage = ImageIO.read(file);
            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File(absolutePath.substring(0, absolutePath.length() - 4) + ".jpg"));
            //删除原来的png格式图片
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把职位的公司名称、公司信息赋上值
     *
     * @param list 已发布的职位列表 或 收藏的职位列表
     * @return
     */
    public List<ReleasePosition> getCompanyInfo(List<ReleasePosition> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
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
     * 职位发布者的信息赋上值
     * @param list
     * @return
     */
    public List<ReleasePosition> getPublisherInfo(List<ReleasePosition> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        for (ReleasePosition rp : list) {
            //职位发布者id
            Long userId = rp.getUserId();
            //职位发布者
            Users user = service.getUserById(userId);
            //职位发布者用户名
            rp.setUsername(user.getUsername());
            //职位发布者用户号 19位
            rp.setUserLid(user.getUserId());
            rp.setAuth(user.getAuth());
        }
        return list;
    }

    public static void main(String[] args) {
        Util util = new Util();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            if (i % 2 == 0) {
                messageList.add(new Message("qq", "x", "x"));
            } else if (i % 2 == 0) {
                messageList.add(new Message("ww", "x", "x"));
            } else if (i % 3 == 0) {
                messageList.add(new Message("ee", "x", "x"));
            } else if (i % 4 == 0) {
                messageList.add(new Message("rr", "x", "x"));
            } else {
                messageList.add(new Message("tt", "x", "x"));
            }
        }
        for (Message m : messageList) {
            System.out.println(m);
        }

        Map<String, List<Message>> stringListMap = util.msgSortOut(messageList, "lichil");
        Set<String> strings = stringListMap.keySet();
        for (String s : strings) {
            List<Message> messages = stringListMap.get(s);
            System.out.println("key:" + s);
            for (Message mm : messages) {
                System.out.println(mm);

            }
        }
    }
}
