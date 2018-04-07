package com.pkast.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public class BeanUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    public static <T> T getBean(String beanName){
        ApplicationContext appCtx = ContextLoaderListener.getCurrentWebApplicationContext();

        if(appCtx == null){
            LOGGER.error("app context is null .");
            return null;
        }
        return (T)appCtx.getBean(beanName);
    }
}
