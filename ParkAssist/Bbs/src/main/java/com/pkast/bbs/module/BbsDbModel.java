package com.pkast.bbs.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class BbsDbModel extends PublishBbsBase{

    private String type;

    private String properties;

    public BbsDbModel(){

    }

    public BbsDbModel(String type, Map<String, Object> properties){
        Logger logger = LoggerFactory.getLogger(BbsDbModel.class);
        this.type = type;

        Stream.of(PublishBbsBase.class.getDeclaredFields())
                .map(field -> field.getName())
                .forEach(property ->{
                    try {
                        Object propVal = properties.remove(property);
                        if(propVal == null){
                            return;
                        }
                        BeanUtils.setProperty(BbsDbModel.this, property, propVal);
                    } catch (IllegalAccessException|InvocationTargetException e) {
                        logger.error("set basic property error", e);
                    }
                });
        try {
            this.properties = new ObjectMapper().writeValueAsString(properties);
        } catch (JsonProcessingException e) {
            logger.error("get ext properties err.", e);
        }
    }

    public Map<String, Object> getPropertyMap(){
        Logger logger = LoggerFactory.getLogger(BbsDbModel.class);
        //小写开头的属性才作为json属性
        Pattern lowCaseStartPattern = Pattern.compile("^[a-z].+");
        try {
            Map<String, Object> propertyMap = new ObjectMapper().readValue(this.properties, new TypeReference<Map<String, Object>>(){});
            Stream.of(PublishBbsBase.class.getDeclaredFields())
                    .map(field -> field.getName())
                    .filter(fieldName -> lowCaseStartPattern.matcher(fieldName).matches())
                    .forEach(property ->{
                        try {
                            propertyMap.put(property, BeanUtils.getProperty(BbsDbModel.this, property));
                        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
                            logger.error("get basic property error", e);
                        }
                    });
            return propertyMap;
        } catch (IOException e) {
            logger.error("read ext properties error.", e);
        }
        return new HashMap<>(0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    protected String getBbsType() {
        return "";
    }

    @Override
    public BbsItem toBbsItem(){
        throw new UnsupportBbsTypeException();
    }
}
