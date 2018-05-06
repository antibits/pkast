package com.pkast.user.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkast.modules.Resp;
import com.pkast.modules.RespRetCode;
import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.user.model.EncryUserInfo;
import com.pkast.utils.CheckUser;
import com.pkast.version.Version;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/pkast/user/" + Version.V_0_0_1 + "/")
@JsonSerialize
public class UserService{

    @Autowired
    private UserBusiness userImpl;

    public void setUserImpl(UserBusiness userImpl) {
        this.userImpl = userImpl;
    }

    @RequestMapping(method = RequestMethod.POST, path =  "add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int addUser(@RequestBody UserInfo user){
        return userImpl.addUser(user).getVal();
    }

    @RequestMapping(method = RequestMethod.POST, path = "edit-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int editUser(@RequestBody UserInfo user){
        return userImpl.editUser(user).getVal();
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
    public Resp<String> getUserByCarNo(@RequestParam("wxNo") String userWxNo, @RequestParam("carNo") @Nullable String carNo){
        UserInfo userInfo = userImpl.getUserByCarNo(carNo.toUpperCase());
        if(userInfo == null){
            return Resp.makeResp(RespRetCode.RET_FAIL);
        }
        return Resp.makeResp(userInfo.getPhoneNum());
    }

    @RequestMapping(method = RequestMethod.GET, path = "get-phone-bywx", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckUser
    public Resp<String> getUserByWx(@RequestParam("wxNo") String userWxNo, @RequestParam("otherWxNo")String otherWxNo){
        UserInfo userInfo = userImpl.getUserByWxNo(otherWxNo);
        if(userInfo == null){
            return Resp.makeResp(RespRetCode.RET_FAIL);
        }
        return Resp.makeResp(userInfo.getPhoneNum());
    }

    /**
     * 使用login返回的js_code和userInfo中的用户encryptedData和iv数据，解密获取用户uniqId
     */
    @RequestMapping(method = RequestMethod.POST, path = "get-user-id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resp<String> requestUserWxNo(@RequestBody EncryUserInfo encryUserInfo){
        String userUniqId = userImpl.requestUserWxNo(encryUserInfo);
        if(StringUtils.isEmpty(userUniqId)){
            return Resp.makeResp(RespRetCode.RET_FAIL);
        }
        return Resp.makeResp(userUniqId);
    }
}
