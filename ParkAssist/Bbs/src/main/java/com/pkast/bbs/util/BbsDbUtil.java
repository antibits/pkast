package com.pkast.bbs.util;

import com.pkast.basicconfig.RouteConfig;
import com.pkast.modules.LocationInfo;
import com.pkast.utils.CollectionUtil;
import com.pkast.utils.RestUtil;
import com.pkast.version.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BbsDbUtil {
    private static final Map<String, String> xiaoquId2DbName = new HashMap<>();

    private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();

    private static final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();

    private static  final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();

    public static String getDbName(String xiaoquId){
        readLock.lock();
        try {
            String dbName = xiaoquId2DbName.get(xiaoquId);
            if(dbName != null){
                return dbName;
            }
        }finally {
            readLock.unlock();
        }
        writeLock.lock();
        try {
            String dbName = xiaoquId2DbName.get(xiaoquId);
            if(dbName != null){
                return dbName;
            }
            LocationInfo xiaoquInfo = getLocationInfo(xiaoquId);
            if(xiaoquInfo == null){
                return null;
            }
            xiaoquId2DbName.put(xiaoquId, xiaoquInfo.getXiaoqu_db_id());
            return xiaoquInfo.getXiaoqu_db_id();
        }finally {
            writeLock.unlock();
        }
    }

    private static LocationInfo getLocationInfo(String xiaoquId){
        String locationInfoUrl = RouteConfig.getInstance().getLocationServiceBasePath() + Version.V_0_0_1 + "/" + "get-location";
        Map<String, String> params = CollectionUtil.tinyMap("xiaoquId", xiaoquId);

        return RestUtil.get(locationInfoUrl, params, LocationInfo.class);
    }

}
