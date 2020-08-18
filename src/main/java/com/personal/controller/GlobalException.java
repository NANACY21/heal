package com.personal.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * 全局异常类
 * @author 李箎
 */
@ControllerAdvice
@Configuration
public class GlobalException {

   /* @ExceptionHandler(value={java.lang.NullPointerException.class})
    public ModelAndView nullExceptionHandler(Exception e){
        ModelAndView mv = new ModelAndView();
        System.out.println(e.getMessage());
        mv.addObject("error", e.toString());
        mv.setViewName("error1");
        return mv;
    }

    @ExceptionHandler(value={java.lang.ArithmeticException.class})
    public ModelAndView arithmeticExceptionHandler(Exception e){
        ModelAndView mv = new ModelAndView();
        System.out.println(e.getMessage());
        mv.addObject("error", e.toString());
        mv.setViewName("error2");
        return mv;
    }*/

    /**
     * 解析器
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        prop.put("java.lang.NullPointerException", "error1");
        prop.put("java.lang.ArithmeticException", "error2");
        //放到解析器里
        smer.setExceptionMappings(prop);
        return smer;
    }
}
