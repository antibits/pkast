package com.pkast.bbs.module;

import com.pkast.utils.CheckValidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PublishBbsXWQS extends PublishBbsBase {
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

    @Override
    protected String getBbsType() {
        return "XWQS";
    }

    @Override
    public BbsItem toBbsItem() {
        return new BbsItemBuilder()
                .append(BbsItemBuilder.EmphasizeLevel.L3, "【寻】")
                .append("本人于")
                .append(BbsItemBuilder.EmphasizeLevel.L2, dayAgo != 0? dayAgo + "天前": "今天")
                .append("丢失")
                .append(BbsItemBuilder.EmphasizeLevel.L1, shortDesc)
                .append("，如有拾到者，请与本人联系！当面重谢！")
                .setContactWxNo(getCreater())
                .build();
    }

    @Override
    public CheckValidUtil.CHECK_INVALID_CODE checkValid() {
        if(dayAgo < 0){
            return CheckValidUtil.CHECK_INVALID_CODE.DAY_INVALID;
        }
        if(StringUtils.isBlank(shortDesc)){
            return CheckValidUtil.CHECK_INVALID_CODE.SHORT_DESC_INVALID;
        }
        return CheckValidUtil.CHECK_INVALID_CODE.VALID_OK;
    }
}
