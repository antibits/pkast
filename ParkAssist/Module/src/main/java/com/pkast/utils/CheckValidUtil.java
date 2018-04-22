package com.pkast.utils;

import com.pkast.basicconfig.RouteConfig;
import com.pkast.modules.Resp;
import com.pkast.modules.RespRetCode;
import com.pkast.modules.UserInfo;
import com.pkast.version.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;

import java.util.regex.Pattern;

public class CheckValidUtil {
    private static final Pattern CAR_NUM_PATTERN = Pattern.compile("^.[A-Z0-9&&[^OI]]{6,7}$");

    private static final Pattern PHONE_NUM_PATTERN = Pattern.compile("^[\\d]{7,13}$");

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

    public static boolean isValidPhoneNum(String phoneNum){
        if(StringUtils.isEmpty(phoneNum)){
            return false;
        }
        return PHONE_NUM_PATTERN.matcher(phoneNum).matches();
    }

    public static boolean isValidCarNum(String carNum){
        if(StringUtils.isEmpty(carNum)){
            return false;
        }
        carNum = carNum.toUpperCase();
        // 车牌号不含O/I
        return CAR_NUM_PATTERN.matcher(carNum).matches();
    }

    public static void main(String[] args) {
        String num = "鄂A48NZ0";
        String num1 = "鄂A48NZO";
        String num2 = "鄂A48NZI";

        System.out.println(num +"is valid num:" + isValidCarNum(num));
        System.out.println(num +"is valid num:" + isValidCarNum(num1));
        System.out.println(num +"is valid num:" + isValidCarNum(num2));
    }
}
