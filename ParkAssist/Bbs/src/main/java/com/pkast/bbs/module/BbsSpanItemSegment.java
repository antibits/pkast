package com.pkast.bbs.module;

import java.util.List;

public class BbsSpanItemSegment extends BbsItemSegment {
    private final String name="span";

    private BbsItemAttr attrs;

    private List<BbsItemSegment> children;

    public String getName() {
        return name;
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
}
