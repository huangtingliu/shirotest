package com.huangtl.realm;

import com.huangtl.dao.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    private static final String REALM_NAME = "CUSTOM_REALM";

    @Autowired
    private UserDao userDao;

    //private static  Map<String,Set<String>> PERMISSIONS;
    //private static  Map<String,Set<String>> ROLES;

    /**
     * 授权部分
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByName(userName);
        Set<String> permissions = getPermissionsByRoles(userName);
        //authorizationInfo.addRoles(roles);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        //System.out.println(authorizationInfo.getRoles().contains("admin"));//竟然是false 你妹的
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    private Set<String> getPermissionsByRoles(String userName) {
        //if(null == PERMISSIONS.get(userName)){
            Set<String> permissions = new HashSet<>(userDao.getPermissionsByUserName(userName));
            //PERMISSIONS.put(userName,permissions);
            return permissions;
        //}
        //return PERMISSIONS.get(userName);
    }

    private Set<String> getRolesByName(String userName) {
        //if(null == ROLES.get(userName)) {
            Set<String> roles = new HashSet<>(userDao.getRolesByUserName(userName));
            //ROLES.put(userName,roles);
            return roles;
        //}
        //return ROLES.get(userName);
    }

    /**
     * 认证部分
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String password = getPasswordByUserName(userName);
        if(null == password){
            return  null;
        }

        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,REALM_NAME);

        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        String password = userDao.getPasswordByUserName(userName);
        return password;
    }
}
