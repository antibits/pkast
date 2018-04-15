package com.pkast.location.impl;

import com.pkast.location.dao.LocationDao;
import com.pkast.location.itf.LocationBusiness;
import com.pkast.modules.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationBusinessImpl implements LocationBusiness {

    @Autowired
    private LocationDao locationDao;

    public boolean addLocation(LocationInfo location) {
        LocationInfo existLocation = locationDao.getLocationById(location.getId());
        if(existLocation != null){
            location.setLocat_x_min(Math.min(location.getLocat_x_min(), existLocation.getLocat_x_min()));
            location.setLocat_x_max(Math.max(location.getLocat_x_max(), existLocation.getLocat_x_max()));
            location.setLocat_y_min(Math.min(location.getLocat_y_min(), existLocation.getLocat_y_min()));
            location.setLocat_y_max(Math.min(location.getLocat_y_max(), existLocation.getLocat_y_max()));
        }

        return locationDao.insertLocation(location) > 0 ? true : false;
    }
}
