package com.pkast.location.dao;

import com.pkast.location.mapper.LocationMapper;
import com.pkast.modules.LocationInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public LocationMapper getMapper(){
        return sqlSessionTemplate.getMapper(LocationMapper.class);
    }

    public int insertLocation(LocationInfo location){
        return getMapper().addLocation(location);
    }

    public LocationInfo getLocationById(String id){
        return getMapper().getLocationById(id);
    }

    public void initDb(String dbId){
        getMapper().initDb(dbId);
    }
}
