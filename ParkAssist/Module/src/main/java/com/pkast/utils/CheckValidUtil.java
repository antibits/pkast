package com.pkast.utils;

import com.pkast.basicconfig.RouteConfig;
import com.pkast.modules.Resp;
import com.pkast.modules.RespRetCode;
import com.pkast.modules.UserInfo;
import com.pkast.version.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;

public class CheckValidUtil {

    /**
     * @see #getRouteConfig()
     */
    private static RouteConfig routeConfig = null;

    private static synchronized RouteConfig getRouteConfig(){
        if(routeConfig == null){
            routeConfig = BeanUtil.getBean("routeConfig");
        }
        return routeConfig;
    }

    public static boolean isWxValid(String wxNo){
        boolean isBlank = StringUtils.isBlank(wxNo);
        if(isBlank){
            return false;
        }
        return isUserExists(wxNo);
    }

    public static boolean isUserExists(String wxNo){
        String userInfoUrl = getRouteConfig().getUserServiceBasePath() + Version.V_0_0_1 + "/get-user-bywx";
        Resp<UserInfo> respUserInfo = RestUtil.get(userInfoUrl, CollectionUtil.tinyMap("wxNo", wxNo), new ParameterizedTypeReference<Resp<UserInfo>>() {});
        return respUserInfo != null &&  RespRetCode.RET_SUCCESS.value() == respUserInfo.getRetCode() && respUserInfo.getData() != null;
    }
}
