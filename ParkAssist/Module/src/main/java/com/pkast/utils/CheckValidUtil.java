package com.pkast.utils;

import com.pkast.basicconfig.RouteConfig;
import com.pkast.version.UserVersion;
import org.apache.commons.lang3.StringUtils;

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
        String userInfoUrl = getRouteConfig().getUserServiceBasePath() + "/pkast/user/" + UserVersion.V_0_0_1 + "/get-user-bywx";

        return true;
    }
}
