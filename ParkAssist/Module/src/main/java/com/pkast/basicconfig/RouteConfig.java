package com.pkast.basicconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class RouteConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteConfig.class);

    private String innerRoute ;

    private String userServiceBasePath;

    private String locationServiceBasePath;

    private String bbsServiceBasePath;

    public void setRouteConfigProps(Resource routeConfigProps){
        Properties prop = new Properties();
        try {
            prop.load(routeConfigProps.getInputStream());
            innerRoute = String.valueOf(prop.get("inner-route"));
            userServiceBasePath = String.valueOf(prop.get("user-service-base-path"));
            locationServiceBasePath = String.valueOf(prop.get("location-service-base-path"));
            bbsServiceBasePath = String.valueOf(prop.get("bbs-service-base-path"));
        } catch (IOException e) {
            LOGGER.error("set route properties err.", e);
        }
    }

    public String getUserServiceBasePath() {
        return innerRoute + "/" + userServiceBasePath;
    }

    public String getLocationServiceBasePath() {
        return innerRoute + "/" + locationServiceBasePath;
    }

    public String getBbsServiceBasePath() {
        return innerRoute + "/" + bbsServiceBasePath;
    }
}
