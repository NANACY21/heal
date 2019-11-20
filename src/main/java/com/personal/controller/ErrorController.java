package com.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 只要抛出异常就跳到error.html
 */
@Controller
public class ErrorController {
    /**
     * 没测出来
     * @param model
     * @return
     */
    @RequestMapping("/e_test")
    public String test1(Model model) {

        String string = null;
        string.length();
//        if (string == null) {
//            model.addAttribute("error", "java.lang.NullPointerException");
//            return "error";
//        }

        return "index";
    }
    @RequestMapping("/e_test2")
    public String test2() {
        System.out.println(1 / 0);
        return "index";
    }


    /**针对处理自定义的异常
     * 捕获这种异常并处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {java.lang.NullPointerException.class})
//    @ExceptionHandler(value = {java.lang.Exception.class})
    public ModelAndView nullExceptionHandler(Exception e) {
        ModelAndView mav = new ModelAndView();
        System.out.println(e.getMessage()+"nullLLL");

        mav.addObject("error", e.toString());
        mav.setViewName("error1");
        return mav;
    }

    @ExceptionHandler(value = {java.lang.ArithmeticException.class})
    public ModelAndView arithmeticExceptionHandler(Exception e) {
        ModelAndView mav = new ModelAndView();
        System.out.println(e.getMessage()+"nullLLL");

        mav.addObject("error", e.toString());
        mav.setViewName("error1");
        return mav;
    }
}
