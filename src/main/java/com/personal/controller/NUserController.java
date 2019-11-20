package com.personal.controller;

import com.personal.pojo.NUser;
import com.personal.service.NUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class NUserController {
    @Autowired
    private NUserService service;

    @RequestMapping("/{path}")//adduser
    public String pages(@PathVariable String path){
        System.out.println(path + "-------------------------");//adduser   addduser.html
        return  path;//addNUser.html
    }

    @RequestMapping("/showpage")//http://127.0.0.1:8080/showpage
    public String showpage(@ModelAttribute("user") NUser nUser){
        return  "addNUser";
    }




    /**urlparrten和跳转的页面采用不同命名
     * 添加NUser ok
     * @param nUser
     * @param br
     * @return
     */
    @RequestMapping("/addnuser1")
    public String addNUser(@Valid @ModelAttribute("user") NUser nUser, BindingResult br) {
        if (br.hasErrors()) {
            List<ObjectError> allErrors = br.getAllErrors();
            for (ObjectError oe : allErrors) {
                System.out.println(oe.getDefaultMessage());
            }
            return "addNUser";
        } else {
            service.saveNUser(nUser);
            return "success";
        }
    }

    //入口url！！！
    @RequestMapping("/getAllNUser")
    public String getAllNUser(Model model){
        List<NUser> AllNUser = service.getAllNUser();
        model.addAttribute("AllNUser",AllNUser);
        return "NUser";
    }

    @RequestMapping("/del/{uID}")
    public String deluser(@PathVariable long uID){
        service.delNUserByUID(uID);
        return "redirect:/NUser";
    }


    @RequestMapping("/edit/{uID}")
    public String editUser(@PathVariable long uID,Model model){
        NUser  nUser = service.getNUserByUID(uID);
        model.addAttribute("nUser",nUser);
        return "editNUser";
    }

    //确认修改 ok
    @RequestMapping("/updateNUser")
    public String updateNUser(NUser nUser){
        service.updateNUser(nUser);
        return "redirect:/NUser";
    }
}
