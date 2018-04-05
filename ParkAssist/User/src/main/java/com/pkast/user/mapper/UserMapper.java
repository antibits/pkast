package com.pkast.user.mapper;

import com.pkast.modules.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int addUser(UserInfo user);

    int update(UserInfo user);

    List<UserInfo> getUserByCarNo(@Param("carNo") String carNo);
}
