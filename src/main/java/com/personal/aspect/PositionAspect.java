package com.personal.aspect;

import com.personal.ESService.ElasticSearchUtil;
import com.personal.kafkaService.producer.SystemMessageProducer;
import com.personal.mapper.PositionMapper;
import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;
import com.personal.pojo.Users;
import com.personal.pojo.msg.Message;
import com.personal.service.PositionService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.ObjectPool;
import com.personal.util.Util;
import org.apache.kafka.common.protocol.types.Field;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Order(2)
@Aspect
@Component
public class PositionAspect {

    @Autowired
    private PositionMapper mapper;
    @Autowired
    private SystemMessageProducer producer;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ElasticSearchUtil searchUtil;

    /**
     * 前置通知 查询职位
     * 切点、通知在一起定义
     */
    @Before("execution(public * com.personal.service.impl.PositionServiceImpl.changePositionStatus(..))")
    public void beforePositionService(JoinPoint joinPoint) {
        //获取目标类的参数
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("######这是前置通知，目标类目标方法名" + name);
        //以下方法不加这个前通知
        String[] methodNameList = {"savePosition", "delPosition", "collectPosition"};
        if (Arrays.asList(methodNameList).contains(name)) {
            return;
        }
        //目标类的参数
        System.out.println(Arrays.toString(args));
        Map<String, Object> map = (Map<String, Object>) args[0];
        long positionId = Long.parseLong(map.get("positionId").toString());
        //得到的职位对象
        Position position = mapper.selectByPrimaryKey(positionId);
        //生产者
        ObjectPool.setPosition(position);
    }

    /**
     * 投递职位之后 系统消息通知
     * @param joinPoint
     */
    @After("execution(public * com.personal.service.impl.Job_applyServiceImpl.postResume(..))")
    public void afterPostResume(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Job_apply jobApply = (Job_apply) args[0];
        if (jobApply.getPositionId() == null) {
            //若投递失败 目标方法的参数会置空 不发消息
            return;
        }
        //投简历的人
        Users user = usersService.getUserById(jobApply.getUserId());
        //投递的职位
        Position position = positionService.getPositionById(jobApply.getPositionId());
        String topic = ConstPool.KAFKA_TOPIC1;
        //接收者用户名
        String username = user.getUsername();
        //消息内容
        String content = Util.getTime() + "，你投递了 [" + position.getName() + "] 职位";
        Message message = new Message(ConstPool.SYSTEM_USERNAME, username, content);
        message.setFromId(usersService.getUserIdByUsername(ConstPool.SYSTEM_USERNAME));
        message.setToId(usersService.getUserIdByUsername(username));
        producer.send(topic, com.personal.util.Util.objectToJson(message));
    }

    /**！！！
     * 添加/保存职位之后
     * @param joinPoint
     */
    @After("execution(public * com.personal.service.impl.PositionServiceImpl.savePosition(..))")
    public void afterSavePosition(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //这时职位id已赋值
        Position position = (Position) args[0];
        if (position.getName() == null) {
            return;
        }
        String ESId = searchUtil.getPositionByName(position.getName(), position.getCompanyId());
        if (ESId == null || ESId.length() == 0) {
            String byId = searchUtil.getPositionById(position.getId());
            if (byId == null || byId.length() == 0) {
                searchUtil.savePosition(position, ConstPool.INDEX_NAME);
            } else {
                searchUtil.updatePosition(byId, Util.pojoToMap(position));
            }
        } else {
            searchUtil.updatePosition(ESId, Util.pojoToMap(position));
        }
    }

    /**
     * 职位状态 status 改变之后
     * ES也同步更新
     * @param joinPoint
     */
    @After("execution(public * com.personal.service.impl.PositionServiceImpl.changePositionStatus(..))")
    public void afterChangePositionStatus(JoinPoint joinPoint) {
        Position position = ObjectPool.getPosition();
        if (position == null) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        //目标方法的参数
        Map<String, Object> argMap = (Map<String, Object>) args[0];
        Map<String, Object> map = new HashMap<>();
        //要改成的职位状态
        int status = Integer.parseInt(argMap.get("status").toString());
        map.put("status", status);
        //要更新的职位发布时间
        String releaseTime = "";
        //是要发布职位
        if (status == 1) {
            releaseTime = position.getReleaseTime();
        }
        //是要撤回职位
        else if (status == 0) {

        }
        map.put("releaseTime", releaseTime);
        String ESId = searchUtil.getPositionByName(position.getName(), position.getCompanyId());
        searchUtil.updatePosition(ESId, map);
    }

    /**sql查询不加limit
     * 所有方法名后缀为 Length 的方法的前置通知
     * @param joinPoint
     */
    @Before("execution(* *Length(..))")
    public void beforeGetListLength(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Map<String, Object> arg = (Map<String, Object>) args[0];
        arg.remove("currentPage");
        arg.remove("pageSize");
    }
}
