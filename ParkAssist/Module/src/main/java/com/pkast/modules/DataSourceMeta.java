package com.pkast.modules;

public class DataSourceMeta {
    private String dbDrive;

    private String dbUrl;

    private String userName;

    private String passwd;

    private String dbName;

    public String getDbDrive() {
        return dbDrive;
    }

    public void setDbDrive(String dbDrive) {
        this.dbDrive = dbDrive;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
