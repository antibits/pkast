package com.pkast.db;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 处于安全考虑，数据库连接的用户名和密码不常驻内存，仅连接需要时，才创建。由于有连接池的存在，解密不会太频繁
 */
public class ProxyedDataSource extends PooledDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyedDataSource.class);
    // TODO 定义加密套件名称和密钥库路径

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        super.setPassword(decode(password));
        return super.getConnection(username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        super.setPassword(decode(super.getPassword()));
        return super.getConnection();
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
