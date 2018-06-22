package com.huangtl.controller;

import com.huangtl.dao.UserDao;
import com.huangtl.entity.User;
import com.huangtl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/toLogin")
    public String login(){
        return "login";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/toRegister")
    public String toRegister(HttpServletRequest request){
        return "register";
    }

    @RequestMapping("/register")
    public String register(User user,HttpServletRequest req){
        try {
            if(StringUtils.isEmpty(user.getUserAccount())){
                req.setAttribute("registerMsg","账号不能为空！");
                return "register";
            }
            if(StringUtils.isEmpty(user.getUserName())){
                req.setAttribute("registerMsg","姓名不能为空！");
                return "register";
            }
            if(StringUtils.isEmpty(user.getPassword())){
                req.setAttribute("registerMsg","密码不能为空！");
                return "register";
            }
            user = userService.register(user);
        } catch (Exception e) {

            if(e.getCause().getMessage().startsWith("Duplicate entry")){
                req.setAttribute("registerMsg","该账号已存在！");
            }else {
                req.setAttribute("registerMsg","注册发生未知错误！");
            }
            return "register";
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(user.getUserAccount(),user.getPassword());
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
    public List<User> userManage(Map param){
        List<User> users = userService.getUsers(param);
        return users;
    }

    @ResponseBody
    @RequestMapping("/userDel")
    public String userDel(User user){
        Subject subject = SecurityUtils.getSubject();
        return "userDel,"+subject.getPrincipal();
    }

}
