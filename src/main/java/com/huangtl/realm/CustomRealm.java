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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
        String userAccount = (String) principalCollection.getPrimaryPrincipal();
        //Set<String> roles = getRolesByUserAccount(userAccount);
        Set<String> permissions = getPermissionsByUserAccount(userAccount);
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
        String userAccount = (String) authenticationToken.getPrincipal();
        if (StringUtils.isEmpty(userAccount)) {
            return null;
        }

        String password = getPasswordByUserAccount(userAccount);
        if(null == password){
            return  null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userAccount,password,REALM_NAME);
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userAccount));

        return authenticationInfo;
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.onLogout(principals);

        System.out.println("退出："+principals.getPrimaryPrincipal());
    }

    /**
     * 根据用户名获取密码
     * @param userAccount
     * @return
     */
    private String getPasswordByUserAccount(String userAccount) {
        String password = userDao.getPasswordByUserAccount(userAccount);
        return password;
    }

    /**
     * 根据用户名获取权限
     * @param userAccount
     * @return
     */
    private Set<String> getPermissionsByUserAccount(String userAccount) {
        Set<String> permissions = new HashSet<>(userDao.getPermissionsUrlByUserAccount(userAccount));
        return permissions;
    }

    /**
     * 根据用户名获取角色
     * @param userAccount
     * @return
     */
    private Set<String> getRolesByUserAccount(String userAccount) {
        Set<String> roles = new HashSet<>(userDao.getRolesByUserAccount(userAccount));
        return roles;
    }
}
