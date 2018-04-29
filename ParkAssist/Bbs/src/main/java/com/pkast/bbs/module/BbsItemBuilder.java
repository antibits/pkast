package com.pkast.bbs.module;

import java.util.*;

public class BbsItemBuilder {

    private String contactPhone;
    private List<BbsItemSegment> segments = new LinkedList<>();

    public BbsItem build(){
        BbsItem bbsItem = new BbsItem();
        bbsItem.setChildren(segments);
        bbsItem.setBindtap("call_" + contactPhone);
        return bbsItem;
    }

    public BbsItemBuilder setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
        return this;
    }

    public BbsItemBuilder append(String text){
        BbsTextItemSegment bbsTextItem =  new BbsTextItemSegment();
        bbsTextItem.setText(text);
        segments.add(bbsTextItem);
        return this;
    }

    public BbsItemBuilder append(EmphasizeLevel level, String text){
        BbsSpanItemSegment bbsSpanItem = new BbsSpanItemSegment();
        bbsSpanItem.setAttrs(level.toItemAttr());
        BbsTextItemSegment bbsTextItem = new BbsTextItemSegment();
        bbsTextItem.setText(text);
        bbsSpanItem.setChildren(Arrays.asList(bbsTextItem));
        segments.add(bbsSpanItem);
        return this;
    }

    public enum EmphasizeLevel{
        L1,
        L2,
        L3;

        private final static Map<EmphasizeLevel, BbsItemAttr> level2Attr = new HashMap<>();
        static {
            level2Attr.put(L1, new BbsEmphasizeL1ItemAttr());
            level2Attr.put(L2, new BbsEmphasizeL2ItemAttr());
            level2Attr.put(L3, new BbsEmphasizeL3ItemAttr());
        }
        private BbsItemAttr toItemAttr(){
            return  level2Attr.get(this);
        }
    }
}
