package com.huangtl.dao;

import com.huangtl.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    String getPasswordByUserAccount(String userAccount);

    List<String> getRolesByUserAccount(String userAccount);

    List<String> getPermissionsUrlByUserAccount(String userAccount);

    //List<Map> getPermissionsByUserAccount(String userAccount);

    List<User> getUsers(Map param);

    void registerUser(User user);
}
