package com.pkast.bbs.module;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class PublishBbsBase implements Cloneable{
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishBbsBase.class);

    private String id;

    private String creater;

    private long timestamp = System.currentTimeMillis();

    private String xiaoquId;

    protected PublishBbsBase(){
        protypeRegist();
    }

    protected abstract String getBbsType();

    public abstract BbsItem toBbsItem();

    private void protypeRegist(){
        PublishBbsFactory.getInstance().regist(getBbsType(), this);
    }

    protected PublishBbsBase createNewWith(PublishBbsBase proType, Map<String, Object> properties) throws CloneNotSupportedException {
        PublishBbsBase result = (PublishBbsBase)proType.clone();
        try {
            BeanUtils.populate(result, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("property not support.", e);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getXiaoquId() {
        return xiaoquId;
    }

    public void setXiaoquId(String xiaoquId) {
        this.xiaoquId = xiaoquId;
    }
}
