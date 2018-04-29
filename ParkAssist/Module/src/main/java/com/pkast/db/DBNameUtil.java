package com.pkast.db;

import org.apache.commons.lang3.StringUtils;

public class DBNameUtil {
    private static final String DEFAULT_DB_NAME = "userdb";

    private static final ThreadLocal<String> dbNames = new ThreadLocal<>();

    public static void setDbName(String dbName){
        dbNames.set(dbName);
    }

    public static String getDbName(){
        String dbName = dbNames.get();
        if(StringUtils.isEmpty(dbName)){
            return DEFAULT_DB_NAME;
        }
        return dbName;
    }
}
