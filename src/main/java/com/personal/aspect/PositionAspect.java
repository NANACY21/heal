package com.personal.aspect;

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

    /**
     * 前置通知 查询职位
     * 切点、通知在一起定义
     * PositionServiceImpl类中后缀为Position的方法的前置方法
     */
    @Before("execution(public * com.personal.service.impl.PositionServiceImpl.*Position(..))")
    public void beforePositionService(JoinPoint joinPoint) {
        //获取目标类的参数
        Object[] args = joinPoint.getArgs();
        //目标类目标方法名
        String name = joinPoint.getSignature().getName();
        System.out.println(name);
        if (name == "savePosition" || name=="delPosition") {
            return;
        }
        //目标类的参数
        System.out.println(Arrays.toString(args));
        long positionId = Long.valueOf(String.valueOf(args[0])).longValue();
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
        Users user = usersService.getUserById(jobApply.getUserId());
        Position position = positionService.getPositionById(jobApply.getPositionId());
        String topic = "postResume";
        Message message = new Message("exodus系统", user.getUsername(), "你投递了[" + position.getName() + "]职位");
        producer.send(topic, com.personal.util.Util.objectToJson(message));
    }
}
