package com.pkast.utils;

import com.pkast.modules.DataSourceMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceConfig implements InitializingBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private Map<String,ConfigChangeListener> listeners = new HashMap<>();

    private Map<String, DataSourceMeta> dsMetas = new HashMap<>();

    public DataSourceMeta getDataSourceMeta(String dataSource){
        return dsMetas.get(dataSource);
    }

    public void addConfigChangeListener(String dataSource, ConfigChangeListener listener){
        listeners.put(dataSource, listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO load configuration of datasource;
        LOGGER.info("loading configuration of datasource.");
    }

    public static interface ConfigChangeListener{
        void onConfigChange(DataSourceMeta dataSourceMeta);
    }
}
