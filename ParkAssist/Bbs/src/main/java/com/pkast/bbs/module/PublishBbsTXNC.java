package com.pkast.bbs.module;

import com.pkast.utils.CheckValidUtil;
import org.springframework.stereotype.Component;

@Component
public class PublishBbsTXNC extends PublishBbsBase {
    private String parkNo;

    private String carNo;

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    @Override
    protected String getBbsType() {
        return "TXNC";
    }

    @Override
    public BbsItem toBbsItem() {
        return new BbsItemBuilder()
                .append(BbsItemBuilder.EmphasizeLevel.L1, "【挪】")
                .append(BbsItemBuilder.EmphasizeLevel.L1, carNo)
                .append("的车主，您正占用位于本小区的私家车位：")
                .append(BbsItemBuilder.EmphasizeLevel.L2, parkNo)
                .append("。现车主急需停车，请速联系！")
                .setContactWxNo(getCreater())
                .build();
    }

    @Override
    public CheckValidUtil.CHECK_INVALID_CODE checkValid() {
        if(!CheckValidUtil.isValidParkNo(parkNo)){
            return CheckValidUtil.CHECK_INVALID_CODE.PARK_NUM_INVALID;
        }
        if(!CheckValidUtil.isValidCarNum(carNo)){
            return CheckValidUtil.CHECK_INVALID_CODE.CAR_NUM_INVALID;
        }
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }
}
