package com.pkast.bbs.module;

public class PublishBbsCWZR extends PublishBbsBase {
    private String parkNo;

    private int price = -1;

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private final static PublishBbsBase PROT_TYPE_INSTANCE = new PublishBbsCWZR();
    @Override
    protected String getBbsType() {
        return "CWZR";
    }

    @Override
    public BbsItem toBbsItem() {
        return new BbsItemBuilder()
                .append(BbsItemBuilder.EmphasizeLevel.L3, "【转】")
                .append("现有位于本小区的车位：")
                .append(BbsItemBuilder.EmphasizeLevel.L1, parkNo)
                .append("正在考虑转让。")
                .append("价格：")
                .append(BbsItemBuilder.EmphasizeLevel.L2, price > 0? price + "元/月": "面议")
                .append("，欢迎电话垂询！")
                .build();
    }
}
