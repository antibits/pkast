package com.pkast.bbs.module;

public class BbsTextItemSegment extends BbsItemSegment{
    private final String type = "text";

    private String text;

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
