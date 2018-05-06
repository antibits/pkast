package com.pkast.user.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkast.modules.UserInfo;
import com.pkast.user.business.itf.UserBusiness;
import com.pkast.user.dao.UserDao;
import com.pkast.user.model.DecryptUserData;
import com.pkast.user.model.EncryUserInfo;
import com.pkast.user.model.WxSessionInfo;
import com.pkast.user.utils.AesCbcEecrypter;
import com.pkast.user.utils.Sha1Encrypter;
import com.pkast.utils.CheckValidUtil;
import com.pkast.utils.RestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class UserBusinessImpl implements UserBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBusinessImpl.class);

    private static final String APP_KEY_CONF = "wx_app_conf.properties";

    private static String appId = "" ;

    /**
     * 这一行千万不能提交到github
     */
    private static String appSecretKey = "";

    @Autowired
    private UserDao userDao;

    static {
        Properties appConfigProps = new Properties();
        try {
            appConfigProps.load(UserBusinessImpl.class.getClassLoader().getResourceAsStream(APP_KEY_CONF));
            appId = appConfigProps.getProperty("app.id");
            appSecretKey = appConfigProps.getProperty("app.secret.key");
        } catch (IOException e) {
            LOGGER.error("load app key conf error.", e);
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CheckValidUtil.CHECK_INVALID_CODE addUser(UserInfo user) {
        CheckValidUtil.CHECK_INVALID_CODE validCode = CheckValidUtil.isValidUserInfo(user);
        if (CheckValidUtil.CHECK_INVALID_CODE.VALID_OK != validCode) {
            return validCode;
        }
        userDao.insertUser(user);
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }

    public CheckValidUtil.CHECK_INVALID_CODE editUser(UserInfo user) {
        CheckValidUtil.CHECK_INVALID_CODE validCode = CheckValidUtil.isValidUserInfo(user);
        if (CheckValidUtil.CHECK_INVALID_CODE.VALID_OK != validCode) {
            return validCode;
        }
        userDao.updateUser(user);
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }

    public UserInfo getUserByCarNo(String carNo) {
        if (!CheckValidUtil.isValidCarNum(carNo)) {
            return null;
        }
        return userDao.getUserByCarNo(carNo);
    }

    public UserInfo getUserByWxNo(String userWxNo) {
        return userDao.getUserByWxNo(userWxNo);
    }

    @Override
    public String requestUserWxNo(EncryUserInfo encryUserInfo) {
        if (StringUtils.isBlank(encryUserInfo.getCode())) {
            LOGGER.info("encry user info code empty");
            return null;
        }
        // 先使用code交换
        Map<String, String> sessKeyParams = new HashMap<String, String>();
        sessKeyParams.put("appid", appId);
        sessKeyParams.put("secret", appSecretKey);
        sessKeyParams.put("js_code", encryUserInfo.getCode());
        sessKeyParams.put("grant_type", "authorization_code");
        WxSessionInfo wxSessInfo = RestUtil.get("https://api.weixin.qq.com/sns/jscode2session", sessKeyParams, WxSessionInfo.class);
        if (wxSessInfo != null && StringUtils.isNoneBlank(wxSessInfo.getUnionid())) {
            return wxSessInfo.getUnionid();
        }
        if (wxSessInfo == null || StringUtils.isBlank(wxSessInfo.getSession_key())) {
            LOGGER.error("get wx session info err.");
            return null;
        }

        String shaSign = Sha1Encrypter.encode(encryUserInfo.getUserRawData() + wxSessInfo.getSession_key());
        // sha1 摘要签名算法验证，无篡改
        if (StringUtils.isNoneEmpty(encryUserInfo.getSignature()) && encryUserInfo.getSignature().equals(shaSign)) {
            // 解密数据，提取用户
            AesCbcEecrypter aesCbcEncrypt = AesCbcEecrypter.initAsDecrypt(wxSessInfo.getSession_key(), encryUserInfo.getIv());
            if (aesCbcEncrypt == null) {
                return null;
            }
            String userDataJson = aesCbcEncrypt.decrypt(encryUserInfo.getEncryptedData());
            try {
                DecryptUserData decryptUserData = userDataJson == null ? null : new ObjectMapper().readValue(userDataJson, DecryptUserData.class);
                return decryptUserData == null ? null : decryptUserData.getUnionId();
            } catch (IOException e) {
                LOGGER.error("parse josn error.", e);
            }
        } else {
            LOGGER.error("signature not match!");
        }
        return null;
    }
}
