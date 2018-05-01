package com.pkast.location.impl;

import com.pkast.location.dao.LocationDao;
import com.pkast.location.itf.LocationBusiness;
import com.pkast.modules.LocationInfo;
import com.pkast.utils.CheckValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LocationBusinessImpl implements LocationBusiness {

    @Autowired
    private LocationDao locationDao;

    @Transactional
    public boolean addLocation(LocationInfo location) {
        if(!CheckValidUtil.isValidLocationInfo(location)){
            return false;
        }
        LocationInfo existLocation = locationDao.getLocationById(location.getId());
        if(existLocation != null){
            location.setLocat_x_min(Math.min(location.getLocat_x_min(), existLocation.getLocat_x_min()));
            location.setLocat_x_max(Math.max(location.getLocat_x_max(), existLocation.getLocat_x_max()));
            location.setLocat_y_min(Math.min(location.getLocat_y_min(), existLocation.getLocat_y_min()));
            location.setLocat_y_max(Math.max(location.getLocat_y_max(), existLocation.getLocat_y_max()));

            if(location.equals(existLocation)){
                return true;
            }
        }
        locationDao.insertLocation(location);
        return true;
    }

    public LocationInfo getLocationInfo(String xiaoquId) {
        return locationDao.getLocationById(xiaoquId);
    }

    @Override
    public void initXiaoquDb(String xiaoquId) {
        LocationInfo insertedLocatInfo = locationDao.getLocationById(xiaoquId);
        locationDao.initDb(insertedLocatInfo.getXiaoqu_db_id());
    }
}
