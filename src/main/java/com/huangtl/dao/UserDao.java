package com.huangtl.dao;

import java.util.List;

public interface UserDao {

    String getPasswordByUserName(String userName);

    List<String> getRolesByUserName(String userName);

    List<String> getPermissionsByUserName(String userName);
}
