package com.pkast.user.business.itf;

import com.pkast.modules.UserInfo;

public interface UserBusiness {
    boolean addUser(UserInfo user);

    boolean editUser(UserInfo user);

    UserInfo getUserByCarNo(String carNo);
}
