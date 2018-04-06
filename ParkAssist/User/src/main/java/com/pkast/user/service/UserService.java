package com.pkast.user.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkast.modules.Resp;
import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.utils.RequestConsumers;
import com.pkast.utils.RequestProducers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/pkast/user/")
@JsonSerialize
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserBusiness userImpl;

    public void setUserImpl(UserBusiness userImpl) {
        this.userImpl = userImpl;
    }

    @RequestMapping(method = RequestMethod.POST, path = "add-user", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public boolean addUser(@RequestBody UserInfo user){
        return userImpl.addUser(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "edit-user", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public boolean editUser(@RequestBody UserInfo user){
        return userImpl.editUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "get-user-bycar", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public Resp<UserInfo> getUserByCarNo(@RequestParam("carNo") String carNo){
        LOGGER.info("carNo : {}", carNo);
        UserInfo userInfo = userImpl.getUserByCarNo(carNo);
        if(userInfo == null){
            return Resp.makeResp();
        }
        return Resp.makeResp(userInfo);
    }
}
