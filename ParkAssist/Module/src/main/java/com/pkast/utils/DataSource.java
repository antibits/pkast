package com.pkast.utils;

import com.pkast.modules.DataSourceMeta;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component("dataSource")
public class DataSource extends BasicDataSource implements DataSourceConfig.ConfigChangeListener, InitializingBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);

    private String dataSource;

    @Autowired
    private DataSourceConfig dsConfig;

    private DataSourceMeta dsMeta;

    public void setDsConfig(DataSourceConfig dsConfig) {
        this.dsConfig = dsConfig;
    }

    public Connection getConnection() throws SQLException {
        return null;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.dataSource = System.getProperty("pkast.datasource");
        LOGGER.error("datasource :{}", this.dataSource);
        dsConfig.addConfigChangeListener(dataSource, this);
    }

    @Override
    public void onConfigChange(DataSourceMeta dataSourceMeta) {
        LOGGER.error("datasource config change :{}", dataSource);
        this.dsMeta = dataSourceMeta;
        // TODO reset properties of super basicdatasource
    }
}
