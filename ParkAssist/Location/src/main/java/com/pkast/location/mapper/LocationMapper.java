package com.pkast.location.mapper;

import com.pkast.modules.LocationInfo;
import org.apache.ibatis.annotations.Param;

public interface LocationMapper {
    int addLocation(LocationInfo location);

    LocationInfo getLocationById(@Param("id") String id);
}
