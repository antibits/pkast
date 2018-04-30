package com.pkast.bbs.module;

public class PublishBbsSWZL extends PublishBbsBase {
    private String shortDesc;

    private int dayAgo = 0;

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getDayAgo() {
        return dayAgo;
    }

    public void setDayAgo(int dayAgo) {
        this.dayAgo = dayAgo;
    }

    private final static PublishBbsBase PROT_TYPE_INSTANCE = new PublishBbsSWZL();
    @Override
    protected String getBbsType() {
        return "SWZL";
    }

    @Override
    public BbsItem toBbsItem() {
        return new BbsItemBuilder()
                .append(BbsItemBuilder.EmphasizeLevel.L3, "【拾】")
                .append("本人于")
                .append(BbsItemBuilder.EmphasizeLevel.L2, dayAgo != 0? dayAgo + "天前": "今天")
                .append("拾到")
                .append(BbsItemBuilder.EmphasizeLevel.L1, shortDesc)
                .append("一个，如有遗失者，请速与本人联系！")
                .build();
    }
}
