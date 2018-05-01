package com.pkast.bbs.module;

import java.util.*;

public class BbsItemBuilder {

    private String contactWxNo;
    private List<BbsItemSegment> segments = new LinkedList<>();

    public BbsItem build(){
        BbsItem bbsItem = new BbsItem();
        bbsItem.setChildren(segments);
        bbsItem.setCreater(contactWxNo);
        return bbsItem;
    }

    public BbsItemBuilder setContactWxNo(String contactWxNo){
        this.contactWxNo = contactWxNo;
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

        private BbsItemAttr toItemAttr(){
            switch (this){
                case L1:
                    return new BbsEmphasizeL1ItemAttr();
                case L2:
                    return new BbsEmphasizeL2ItemAttr();
                case L3:
                    return new BbsEmphasizeL3ItemAttr();
            }
            return null;
        }
    }
}
