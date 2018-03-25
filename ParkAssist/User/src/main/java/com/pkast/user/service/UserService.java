package com.pkast.user.service;

import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.utils.RequestConsumers;
import com.pkast.utils.RequestProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/pkast/user/")
public class UserService {

    @Autowired
    private UserBusiness userImpl;

    public void setUserImpl(UserBusiness userImpl) {
        this.userImpl = userImpl;
    }

    @RequestMapping(method = RequestMethod.POST, path = "add-user", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public boolean addUser(@RequestBody UserInfo user){
//        return userImpl.addUser(user);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, path = "edit-user", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public boolean editUser(@RequestBody UserInfo user){
//        return userImpl.editUser(user);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, path = "get-user-bycar", consumes = RequestConsumers.JSON, produces = RequestProducers.JSON)
    public UserInfo getUserByCarNo(String carNo){
//        return userImpl.getUserByCarNo(carNo);
        return new UserInfo();
    }
}
