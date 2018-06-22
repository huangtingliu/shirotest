package com.huangtl.service;

import com.huangtl.dao.UserDao;
import com.huangtl.entity.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户服务
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User register(User user) throws Exception{

        user.setPassword(generatePassword(user.getUserAccount(),user.getPassword()));
        userDao.registerUser(user);

        return user;
    }

    public List<User> getUsers(Map param){
        return userDao.getUsers(param);
    }

    /**
     * 生成密码策略
     * 利用Md5Hash加密生成密文密码
     * 特别注意：
     * 加密算法需要跟spring-shiro.xml中id为credentialsMatcher的bean的hashAlgorithmName属性一致
     * 加密次数需要跟spring-shiro.xml中id为credentialsMatcher的bean的hashIterations属性一致
     * 加密盐则与自定义{@link com.huangtl.realm.CustomRealm realm}类
     * 中的{@link com.huangtl.realm.CustomRealm#doGetAuthenticationInfo  doGetAuthenticationInfo}方法
     * 中的{@code authenticationInfo.setCredentialsSalt}这行代码的参数一致
     * @param userAccount 用户名当做salt加密盐
     * @param passowrd 明文密码
     * @return 密文密码
     */
    private String generatePassword(String userAccount,String passowrd){
        Md5Hash md5Hash = new Md5Hash(passowrd, userAccount);
        return md5Hash.toString();
    }

}
