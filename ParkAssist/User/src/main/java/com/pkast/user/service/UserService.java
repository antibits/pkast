package com.pkast.user.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkast.modules.Resp;
import com.pkast.modules.RespRetCode;
import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.utils.CheckUser;
import com.pkast.version.UserVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/pkast/user/" + UserVersion.V_0_0_1 + "/")
@JsonSerialize
public class UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserBusiness userImpl;

    public void setUserImpl(UserBusiness userImpl) {
        this.userImpl = userImpl;
    }

    @RequestMapping(method = RequestMethod.POST, path =  "add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addUser(@RequestBody UserInfo user){
        return userImpl.addUser(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "edit-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean editUser(@RequestBody UserInfo user){
        return userImpl.editUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "get-user-bywx", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resp<UserInfo> getUserByWx(@RequestParam("wxNo") String userWxNo){
        UserInfo userInfo = userImpl.getUserByWxNo(userWxNo);
        if(userInfo == null){
            return Resp.makeResp(RespRetCode.RET_FAIL);
        }
        return Resp.makeResp(userInfo);
    }

    @RequestMapping(method = RequestMethod.GET, path = "get-user-bycar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckUser
    public Resp<UserInfo> getUserByCarNo(@RequestParam("wxNo") String userWxNo, @RequestParam("carNo") @Nullable String carNo){
        LOGGER.debug("carNo : {}", carNo);
        UserInfo userInfo = userImpl.getUserByCarNo(carNo);
        if(userInfo == null){
            return Resp.makeResp(RespRetCode.RET_FAIL);
        }
        return Resp.makeResp(userInfo);
    }
}
