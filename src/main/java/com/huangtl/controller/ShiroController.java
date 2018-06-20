package com.huangtl.controller;

import com.huangtl.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.org.mozilla.javascript.internal.Token;

@Controller
public class ShiroController {

    @RequestMapping("/toLogin")
    public String login(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            subject.getSession().setAttribute("loginMsg","账号密码不正确！");
            return "login";
        }catch (UnknownAccountException e){
            //e.printStackTrace();
            subject.getSession().setAttribute("loginMsg","账号不存在！");
            return "login";
        }
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @ResponseBody
    @RequestMapping("/userManage")
    public String userManage(User user){
        Subject subject = SecurityUtils.getSubject();
        return "userManage,"+subject.getPrincipal();
    }

    @ResponseBody
    @RequestMapping("/userDel")
    public String userDel(User user){
        Subject subject = SecurityUtils.getSubject();
        return "userManage,"+subject.getPrincipal();
    }

}
