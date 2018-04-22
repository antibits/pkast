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
        userDao.insertUser(user);
        return true;
    }

    public boolean editUser(UserInfo user) {
        userDao.updateUser(user);
        return true;
    }

    public UserInfo getUserByCarNo(String carNo) {
        return userDao.getUserByCarNo(carNo);
    }

    public UserInfo getUserByWxNo(String userWxNo) {
        return userDao.getUserByWxNo(userWxNo);
    }
}
