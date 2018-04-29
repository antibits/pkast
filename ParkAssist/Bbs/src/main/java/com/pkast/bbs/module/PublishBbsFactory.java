package com.pkast.bbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PublishBbsFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishBbsFactory.class);

    private static PublishBbsFactory instance = null;

    private static Map<String, PublishBbsBase> supportPublishBbs = new HashMap<>();

    private PublishBbsFactory(){

    }
    public static synchronized PublishBbsFactory getInstance(){
        if(instance == null){
            instance = new PublishBbsFactory();
        }
        return instance;
    }

    public void regist(String type, PublishBbsBase protype){
        if(supportPublishBbs.containsKey(type)){
            return;
        }
        synchronized (supportPublishBbs){
            supportPublishBbs.putIfAbsent(type, protype);
        }
    }

    public static PublishBbsBase makePublishBbs(String type, Map<String, Object> properties){
        PublishBbsBase protype ;

        synchronized (supportPublishBbs){
            protype = supportPublishBbs.get(type);
        }
        if(protype == null){
            return null;
        }
        try {
            return protype.createNewWith(protype, properties);
        } catch (CloneNotSupportedException e) {
            LOGGER.error("make bbs with properties err.", e);
        }
        return null;
    }

}
