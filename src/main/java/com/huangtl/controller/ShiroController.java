package com.huangtl.controller;

import com.huangtl.entity.User;
import com.huangtl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.org.mozilla.javascript.internal.Token;

@Controller
public class ShiroController {

    @ResponseBody
    @RequestMapping("/orderManage")
    public String orderManage(User user){
        Subject subject = SecurityUtils.getSubject();
        return "orderManage,"+subject.getPrincipal();
    }
    @ResponseBody
    @RequestMapping("/orderDel")
    public String orderDel(User user){
        Subject subject = SecurityUtils.getSubject();
        return "orderDel,"+subject.getPrincipal();
    }

}
