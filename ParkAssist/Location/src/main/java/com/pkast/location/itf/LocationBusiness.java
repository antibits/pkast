package com.pkast.location.itf;

import com.pkast.modules.LocationInfo;

public interface LocationBusiness {
    boolean addLocation(LocationInfo location);

    LocationInfo getLocationInfo(String xiaoquId);

    void initXiaoquDb(String xiaoquId);
}
