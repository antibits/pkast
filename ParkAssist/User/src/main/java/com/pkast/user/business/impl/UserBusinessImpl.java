package com.pkast.user.business.impl;

import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean addUser(UserInfo user) {
        return false;
    }

    public boolean editUser(UserInfo user) {
        return false;
    }

    public UserInfo getUserByCarNo(String carNo) {
        return null;
    }
}
