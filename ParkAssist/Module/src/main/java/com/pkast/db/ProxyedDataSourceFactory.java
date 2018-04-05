package com.pkast.db;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProxyedDataSourceFactory implements FactoryBean<DataSource>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyedDataSourceFactory.class);

    private static final String DB_TAG_IN_URL = "<database>";

    private final Map<String, DataSource> dataSourceCache = new HashMap();

    private final Properties basicFieldProps = new Properties();

    private final Properties driverProps = new Properties();

    private final ReentrantReadWriteLock dsLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readDsLock = dsLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeDsLock = dsLock.writeLock();

    public void setBasicFieldProps(Resource basicFieldConfig) {
        try {
            basicFieldProps.load(basicFieldConfig.getInputStream());
        } catch (IOException e) {
            LOGGER.error("load basic properties err.", e);
        }
    }

    public void setDriverProps(Resource driverConfig) {
        try {
            driverProps.load(driverConfig.getInputStream());
        } catch (IOException e) {
            LOGGER.error("load basic properties err.", e);
        }
    }

    /**
     * 提供数据库
     * @return
     */
    @Override
    public DataSource getObject() {
        String dbName = getDatabaseName();
        readDsLock.lock();
        try{
            // readlock共享锁，只与writeLock互斥
            DataSource ds = dataSourceCache.get(dbName);
            if(ds != null){
                return ds;
            }
        }finally {
            readDsLock.unlock();
        }
        writeDsLock.lock();
        try{
            // 再次判断。writeLock互斥，保证不重复产生
            DataSource ds = dataSourceCache.get(dbName);
            if(ds == null){
                ds = new ProxyedDataSource();
                // 更新url属性，动态添加数据库
                bindDBProperties((PooledDataSource)ds, dbName);
                dataSourceCache.put(dbName, ds);
            }
            return ds;
        }finally {
            writeDsLock.unlock();
        }
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private String getDatabaseName(){
        // TODO 当涉及动态获取数据库时，根据动态数据库运算获取当前数据库实例
        return "userdb";
    }

    /**
     * 设置配置属性
     * @param dataSource
     * @param dbName
     */
    private void bindDBProperties(PooledDataSource dataSource, String dbName){
        LOGGER.error("bind db properties start.");
        MetaObject metaDataSource = SystemMetaObject.forObject(dataSource);
        for(Map.Entry<Object, Object> prop : basicFieldProps.entrySet()){
            String propName = String.valueOf(prop.getKey());
            String propVal = String.valueOf(prop.getValue());
            metaDataSource.setValue(propName, convertValue(metaDataSource, propName, propVal));
        }

        dataSource.setUrl(dataSource.getUrl().replace(DB_TAG_IN_URL, dbName));

        // 设置 driver properties
        dataSource.setDriverProperties(driverProps);
    }

    private Object convertValue(MetaObject metaDataSource, String propertyName, String value) {
        Object convertedValue = value;
        Class<?> targetType = metaDataSource.getSetterType(propertyName);
        if (targetType != Integer.class && targetType != Integer.TYPE) {
            if (targetType != Long.class && targetType != Long.TYPE) {
                if (targetType == Boolean.class || targetType == Boolean.TYPE) {
                    convertedValue = Boolean.valueOf(value);
                }
            } else {
                convertedValue = Long.valueOf(value);
            }
        } else {
            convertedValue = Integer.valueOf(value);
        }

        return convertedValue;
    }
}
