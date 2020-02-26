package com.personal.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 切面类
 * <p>
 * 所有请求的方法执行之前要执行
 * 参考：https://www.cnblogs.com/wangshen31/p/9379197.html
 * <p>
 * 关于执行顺序：https://blog.csdn.net/huxiaodong1994/article/details/82991828
 *
 * @author 李箎
 */
//多个切面时的执行顺序
@Order(1)
@Aspect
@Component
@Service
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 切入点描述 这个是controller包的切入点
     * 签名，可以理解成这个切入点的一个名称
     */
    @Pointcut("execution(public * com.personal.controller..*.*(..))")
    public void controllerLog() {
    }

    /**
     * 在切入点的方法run之前要干的
     *
     * @param joinPoint
     */
    @Before("controllerLog() || controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            HttpServletRequest request = sra.getRequest();
            //请求内容:
            logger.info("##########浏览器的URL : " + request.getRequestURL().toString());
            logger.info("##########请求的HTTP方法HTTP_METHOD : " + request.getMethod());
            logger.info("##########发出请求的客户端的IP地址 : " + request.getRemoteAddr());
            //某一参数为数组可能不能好好打印
            logger.info("##########前端提交的数据THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                logger.info("##########前端提交的数据控制器参数" + i + 1 + " : " + joinPoint.getArgs()[i].toString());
            }
        }
        //该请求访问的包名及类名
        logger.info("##########前端请求执行的包+类名 : " + joinPoint.getSignature().getDeclaringTypeName());
        //该请求执行的方法名
        logger.info("##########前端请求执行的方法名CLASS_METHOD : " + joinPoint.getSignature().getName());
        //logger.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //logger.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }
}
