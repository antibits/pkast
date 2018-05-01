package com.pkast.user.business.itf;

import com.pkast.modules.UserInfo;
import com.pkast.utils.CheckValidUtil;

public interface UserBusiness {
    CheckValidUtil.CHECK_INVALID_CODE addUser(UserInfo user);

    CheckValidUtil.CHECK_INVALID_CODE editUser(UserInfo user);

    UserInfo getUserByCarNo(String carNo);

    UserInfo getUserByWxNo(String userWxNo);
}
