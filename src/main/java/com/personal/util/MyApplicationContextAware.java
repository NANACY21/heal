package com.personal.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 李箎
 */
@Component
@Lazy(false)
public class MyApplicationContextAware implements ApplicationContextAware {
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * 该方法静态？？
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return ac;
    }

    public static Object getBean(String beanName){
        return ac.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz){
        return (T)ac.getBean(clazz);
    }
}
