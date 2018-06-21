package com.huangtl.filter;

import com.huangtl.dao.UserDao;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 自定义权限控制过滤器
 */
public class CustomShiroFilter extends AuthorizationFilter {

    @Autowired
    private UserDao userDao;

    /**
     * 自定义shiro权限过滤
     * @param servletRequest 请求内容，用于shiro获取登录用户
     * @param servletResponse 响应内容，用于shiro获取登录用户
     * @param o 配置允许的权限，本项目中spring-shiro.xml文件中 shiroFilter类中属性为filterChainDefinitions中value配置的各项值
     *          默认过滤器有anon:表示可以匿名使用; authc:表示需要认证(登录)才能使用，没有参数;roles:表示需同时拥有的角色；perms:表示需同时拥有的权限
     *          自定义：（示例:/userManage xxx["1","2"]），这里的xxx是本类在spring-shiro.xml文件中配置的bean的id
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, Object o) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;

        //如果spring-shiro中配置了特殊请求的根据配置处理
        //if(roles!=null) {
        //    //这里配置为只需拥有配置中的一项角色就可以访问
        //    for (String role : roles) {
        //        if (subject.hasRole(role)) {
        //            return true;
        //        }
        //    }
        //}

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestPath = httpServletRequest.getServletPath()+(null==httpServletRequest.getPathInfo()?"":httpServletRequest.getPathInfo());

        if(subject.isPermitted(requestPath)){
            return true;
        }

        return false;
    }
}
