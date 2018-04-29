package com.pkast.bbs.module;

public class PulishBbsTXNC extends PublishBbsBase {
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
                .append(BbsItemBuilder.EmphasizeLevel.L3, "【挪】")
                .append(BbsItemBuilder.EmphasizeLevel.L1, carNo)
                .append("的车主，你正占用位于本小区的私家车位：")
                .append(BbsItemBuilder.EmphasizeLevel.L2, parkNo)
                .append("。现车主急需停车，请速联系！")
                .build();
    }
}
