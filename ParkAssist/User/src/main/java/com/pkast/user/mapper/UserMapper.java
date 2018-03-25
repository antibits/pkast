package com.pkast.user.mapper;

import com.pkast.modules.UserInfo;

public interface UserMapper {
    int addUser(UserInfo user);

    int update(UserInfo user);

    UserInfo getUserByCarNo(String carNo);
}
