package com.pkast.bbs.module;

import org.springframework.stereotype.Component;

@Component
public class PublishBbsCWCZ extends PublishBbsBase {
    private String parkNo;

    private int period = -1;

    private int price = -1;

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    protected String getBbsType() {
        return "CWCZ";
    }

    @Override
    public BbsItem toBbsItem() {
        return new BbsItemBuilder()
                .append(BbsItemBuilder.EmphasizeLevel.L3, "【租】")
                .append("现有位于本小区的车位：")
                .append(BbsItemBuilder.EmphasizeLevel.L1, parkNo)
                .append("正在出租。")
                .append("出租时间：")
                .append(BbsItemBuilder.EmphasizeLevel.L2, period > 0? period +"个月": "面议")
                .append(",价格：")
                .append(BbsItemBuilder.EmphasizeLevel.L2, price > 0? price + "元/月": "面议")
                .append(",欢迎垂询！")
                .build();
    }
}
