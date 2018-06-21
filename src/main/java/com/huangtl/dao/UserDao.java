package com.huangtl.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {

    String getPasswordByUserName(String userName);

    List<String> getRolesByUserName(String userName);

    List<String> getPermissionsUrlByUserName(String userName);

    List<Map> getPermissionsByUserName(String userName);
}
