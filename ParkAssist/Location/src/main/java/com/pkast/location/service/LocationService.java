package com.pkast.location.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkast.location.itf.LocationBusiness;
import com.pkast.modules.LocationInfo;
import com.pkast.version.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/pkast/location/" + Version.V_0_0_1 + "/")
@JsonSerialize
public class LocationService {

    @Autowired
    private LocationBusiness locationBusiImpl;

    @RequestMapping(method = RequestMethod.POST, path = "add-location",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addLocation(@RequestBody LocationInfo location){
        return locationBusiImpl.addLocation(location);
    }
}
