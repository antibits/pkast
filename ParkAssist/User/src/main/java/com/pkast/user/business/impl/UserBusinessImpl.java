package com.pkast.user.business.impl;

import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.user.dao.UserDao;
import com.pkast.utils.CheckValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CheckValidUtil.CHECK_INVALID_CODE addUser(UserInfo user) {
        CheckValidUtil.CHECK_INVALID_CODE validCode = CheckValidUtil.isValidUserInfo(user);
        if(CheckValidUtil.CHECK_INVALID_CODE.VALID_OK != validCode){
            return validCode;
        }
        userDao.insertUser(user);
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }

    public CheckValidUtil.CHECK_INVALID_CODE editUser(UserInfo user) {
        CheckValidUtil.CHECK_INVALID_CODE validCode = CheckValidUtil.isValidUserInfo(user);
        if(CheckValidUtil.CHECK_INVALID_CODE.VALID_OK != validCode){
            return validCode;
        }
        userDao.updateUser(user);
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }

    public UserInfo getUserByCarNo(String carNo) {
        return userDao.getUserByCarNo(carNo);
    }

    public UserInfo getUserByWxNo(String userWxNo) {
        return userDao.getUserByWxNo(userWxNo);
    }
}
