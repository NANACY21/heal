package com.personal.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李箎
 */
public class ConstPool {

    public static final String SUCCESS = "成功";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String ADD_POSITION_SUCCESS = "新增职位成功";
    public static final String ADD_POSITION_FAIL = "新增职位失败";
    public static final String POSITION_NOT_EXIST = "职位不存在";
    public static final String POSITION_RELEASED = "操作无效";
    public static final String POSITION_RELEASE_SUCCESS = "职位发布成功";
    public static final String POSITION_WITHDRAW_SUCCESS = "职位撤回成功";
    public static final String LOGIN_PW_INCORRECT = "登录密码错误";
    public static final String USER_NOT_EXIST = "用户不存在";
    public static final String USERNAME_EXIST = "用户名已存在";
    public static final String REGISTER_SUCCESS = "用户注册成功";
    public static final String REGISTER_FAIL = "用户注册失败";
    /**
     * session对象中的一个键 username
     */
    public static final String SESSION_KEY1 = "username";
    /**
     * session对象中的一个键 password
     */
    public static final String SESSION_KEY2 = "password";
    public static final String IP = "localhost";

    /**
     * 前端地址
     */
    public static final String WEB_IP = "http://localhost:8085";

    /**
     * generatorConfig.xml文件路径
     */
    public static final String GENERATORCONFIG = "D:\\allproject\\personal\\heal\\src\\main\\resources\\generatorConfig.xml";

    /**
     * 前端用户头像照片存放目录
     */
    public static final String HEAD_PHOTO_SAVE_PATH = "D:\\allproject\\personal\\exodus\\exodus\\src\\assets\\headPhoto\\";
    /**
     * 简历照片存放目录
     */
    public static final String RESUME_PHOTO_SAVE_PATH = "D:\\allproject\\personal\\exodus\\exodus\\src\\assets\\resumePhoto\\";
    /**
     * 广告照片存放目录
     */
    public static final String AD_PHOTO_SAVE_PATH = "D:\\allproject\\personal\\exodus\\exodus\\src\\assets\\ad\\";
    /**
     * 用户创建的JSON文件存放目录
     */
    public static final String JSON_PATH = "D:\\allproject\\personal\\exodus\\exodus\\src\\assets\\e_data";
    /**
     * Kafka的一个topic
     */
    public static final String KAFKA_TOPIC1 = "exodusMsgList";
    public static final String KAFKA_CONNECT_ERROR = "SpringBoot代理的Kafka客户端连接到Kafka服务器出错";
    /**
     * 系统账号用户名
     */
    public static final String SYSTEM_USERNAME = "exodusSystem";

    /**
     * 全局站内搜索历史记录 Redis key的后缀
     */
    public static final String SEARCH_HISTORY = "-search-history";

    /**
     * 收藏的职位
     */
    public static final String COLLECT_POSITION = "-collect-position";
    /**
     * 收藏的公司
     */
    public static final String COLLECT_COMPANY = "-collect-company";

    /**
     * 收藏的简历
     */
    public static final String COLLECT_RESUME = "-collect-resume";

    /**
     * 在线用户
     */
    public static ConcurrentHashMap<String, Object> onlineCount = new ConcurrentHashMap<>();

    /**
     * 本地
     */
    public static final String HOST = "127.0.0.1";

    /**
     * springboot客户端访问ES的端口
     */
    public static final int ES_PORT = 9200;
    public static final String HTTP_SCHEME = "http";
    /**
     * ES 索引名称
     */
    public static final String INDEX_NAME = "job_search_website";

    /**
     * ES 索引类型
     */
    public static final String TYPE = "_doc";
}
