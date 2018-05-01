package com.pkast.utils;

import com.pkast.basicconfig.RouteConfig;
import com.pkast.modules.LocationInfo;
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

    private static final Pattern PRICE_NUM_PATTERN = Pattern.compile("^[1-9][\\d]*$");

    private static final Pattern TIME_NUM_PATTERN = Pattern.compile("^[1-9][\\d]{1,2}$");

    private static final Pattern PARK_NO_PATTERN  = Pattern.compile("^[a-zA-Z\\d]{3,9}$");

    public static boolean isWxValid(String wxNo) {
        boolean isBlank = StringUtils.isBlank(wxNo);
        if (isBlank) {
            return false;
        }
        return isUserExists(wxNo);
    }

    public static boolean isUserExists(String wxNo) {
        String userInfoUrl = RouteConfig.getInstance().getUserServiceBasePath() + Version.V_0_0_1 + "/get-user-bywx";
        Resp<UserInfo> respUserInfo = RestUtil.get(userInfoUrl, CollectionUtil.tinyMap("wxNo", wxNo), new ParameterizedTypeReference<Resp<UserInfo>>() {
        });
        return respUserInfo != null && RespRetCode.RET_SUCCESS.value() == respUserInfo.getRetCode() && respUserInfo.getData() != null;
    }

    public static boolean isValidPhoneNum(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            return false;
        }
        return PHONE_NUM_PATTERN.matcher(phoneNum).matches();
    }

    public static boolean isValidCarNum(String carNum) {
        if (StringUtils.isBlank(carNum)) {
            return false;
        }
        carNum = carNum.toUpperCase();
        // 车牌号不含O/I
        return CAR_NUM_PATTERN.matcher(carNum).matches();
    }

    public static CHECK_INVALID_CODE isValidUserInfo(UserInfo user) {
        if (user == null) {
            return CHECK_INVALID_CODE.USER_INVALID;
        }
        String carNo = user.getCarNo() == null ? null : user.getCarNo().toUpperCase();
        user.setCarNo(carNo);
        if (!isValidCarNum(carNo)) {
            return CHECK_INVALID_CODE.CAR_NUM_INVALID;
        }
        if(!isValidPhoneNum(user.getPhoneNum())){
            return CHECK_INVALID_CODE.PHONE_NUM_INVALID;
        }
        if (StringUtils.isBlank(user.getWxNo())) {
            return CHECK_INVALID_CODE.WXNO_INVALID;
        }
        if (StringUtils.isBlank(user.getXiaoqu())) {
            return CHECK_INVALID_CODE.XIAOQU_ID_INVALID;
        }
        return CHECK_INVALID_CODE.VALID_OK;
    }

    public static boolean isValidLocationInfo(LocationInfo location){
        if(location == null){
            return false;
        }
        if(StringUtils.isBlank(location.getId())){
            return false;
        }
        return true;
    }

    public static boolean isValidParkNo(String parkNo){
        if(StringUtils.isBlank(parkNo)){
            return false;
        }
        return PARK_NO_PATTERN.matcher(parkNo).matches();
    }

    public static boolean isValidPriceNum(String price){
        if(StringUtils.isBlank(price)){
            return false;
        }
        return PRICE_NUM_PATTERN.matcher(price).matches();
    }

    public static boolean isValidPriceNum(int price){
        return 0 <= price && price < Integer.MAX_VALUE;
    }

    public static boolean isValidTimeNum(String time){
        if(StringUtils.isBlank(time)){
            return false;
        }
        return TIME_NUM_PATTERN.matcher(time).matches();
    }

    public static boolean isValidTimeNum(int time){
        return 0 <= time && time < 999;
    }

    public enum CHECK_INVALID_CODE {
        BBS_TYPE_INVALID(-2),
        USER_INVALID(-1),
        VALID_OK(0),
        CAR_NUM_INVALID(1),
        PHONE_NUM_INVALID(2),
        WXNO_INVALID(3),
        XIAOQU_ID_INVALID(4),
        PARK_NUM_INVALID(5),
        PRICE_NUM_INVALID(6),
        TIME_NUM_INVALID(7),
        DAY_INVALID(8),
        SHORT_DESC_INVALID(9);

        private int val;

        private CHECK_INVALID_CODE(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public static void main(String[] args) {
        String num = "鄂A48NZ0";
        String num1 = "鄂A48NZO";
        String num2 = "鄂A48NZI";

        System.out.println(num + "is valid num:" + isValidCarNum(num));
        System.out.println(num + "is valid num:" + isValidCarNum(num1));
        System.out.println(num + "is valid num:" + isValidCarNum(num2));
    }
}
