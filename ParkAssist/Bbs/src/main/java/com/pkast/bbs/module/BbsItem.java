package com.pkast.bbs.module;

import java.util.List;

public class BbsItem {
    private final String name = "div";

    private String creater;

    private String bindtap;

    private BbsItemAttr attrs = new BbsUlItemAttr();

    private List<BbsItemSegment> children;

    public String getName() {
        return name;
    }

    public String getBindtap() {
        return bindtap;
    }

    public void setBindtap(String bindtap) {
        this.bindtap = bindtap;
    }

    public BbsItemAttr getAttrs() {
        return attrs;
    }

    public void setAttrs(BbsItemAttr attrs) {
        this.attrs = attrs;
    }

    public List<BbsItemSegment> getChildren() {
        return children;
    }

    public void setChildren(List<BbsItemSegment> children) {
        this.children = children;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
