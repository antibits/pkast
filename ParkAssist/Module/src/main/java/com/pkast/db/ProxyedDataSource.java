package com.pkast.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 处于安全考虑，数据库连接的用户名和密码不常驻内存，仅连接需要时，才创建。由于有连接池的存在，解密不会太频繁
 */
public class ProxyedDataSource extends BasicDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyedDataSource.class);
    // TODO 定义加密套件名称和密钥库路径

    private static final String DB_TAG_IN_URL = "<database>";

    private final Map<String, PooledDataSource> dataSourceCache = new HashMap();

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

    private PooledDataSource getRealDataSource(){
        String dbName = getDatabaseName();
        readDsLock.lock();
        try{
            // readlock共享锁，只与writeLock互斥
            PooledDataSource ds = dataSourceCache.get(dbName);
            if(ds != null){
                return ds;
            }
        }finally {
            readDsLock.unlock();
        }
        writeDsLock.lock();
        try{
            // 再次判断。writeLock互斥，保证不重复产生
            PooledDataSource ds = dataSourceCache.get(dbName);
            if(ds == null){
                ds = new PooledDataSource();
                // 更新url属性，动态添加数据库
                bindDBProperties(ds, dbName);
                dataSourceCache.put(dbName, ds);
            }
            return ds;
        }finally {
            writeDsLock.unlock();
        }
    }

    private String getDatabaseName(){
        // TODO 当涉及动态获取数据库时，根据动态数据库运算获取当前数据库实例
        return DBNameUtil.getDbName();
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

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getRealDataSource().getConnection(username, decode(password));
    }

    @Override
    public Connection getConnection() throws SQLException {
        PooledDataSource realDataSource = getRealDataSource();
        return realDataSource.getConnection(realDataSource.getUsername(), decode(realDataSource.getPassword()));
    }

    /**
     * 使用加密套件，解析密码
     * @param encryptStr
     * @return
     */
    private String decode(String encryptStr){
        String decodedStr = encryptStr;
        LOGGER.error("decode password from {} to {}", encryptStr, decodedStr);
        return decodedStr;
    }
}
