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

import java.util.HashSet;
import java.util.Set;

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
        //Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
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

    /**
     * 根据用户名获取密码
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        String password = userDao.getPasswordByUserName(userName);
        return password;
    }

    /**
     * 根据用户名获取权限
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissions = new HashSet<>(userDao.getPermissionsUrlByUserName(userName));
        return permissions;
    }

    /**
     * 根据用户名获取角色
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>(userDao.getRolesByUserName(userName));
        return roles;
    }
}
