package com.pkast.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

public class RestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    /**
     * @see #getRestTemplate()
     */
    private static RestTemplate restTemplate = null;

    public static <K, V, M extends Map<K, V>> String makeUrlWithParams(String url, M params){
        StringBuilder sb = new StringBuilder(url);
        if(CollectionUtil.isEmpty(params)){
            return url;
        }
        sb.append("?");
        params.entrySet().forEach(paramEntry->{
            try {
                String paramVal = URLEncoder.encode(String.valueOf(paramEntry.getValue()), "UTF-8");
                sb.append(paramEntry.getKey()).append("=").append(paramVal).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        return sb.substring(0, sb.length() - 1);
    }

    private static synchronized RestTemplate getRestTemplate(){
        if(restTemplate == null){
            restTemplate = BeanUtil.getBean("restTemplate");
        }
        return restTemplate;
    }

    /**
     * get 传参全部通过url。
     * @param url
     * @param urlParams
     * @param respType
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Map<String, String> urlParams, Class<T> respType) {
        return get(url, urlParams, null, new ParameterizedTypeReference<T>() {
            @Override
            public Type getType() {
                return respType;
            }
        });
    }

    /**
     * 提供模板化参数的转换能力
     * @param url
     * @param urlParams
     * @param respTypeParam
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Map<String, String> urlParams, ParameterizedTypeReference<T> respTypeParam){
        return get(url, urlParams, null, respTypeParam);
    }

    /**
     * 提供支持头参数的get请求接口
     * @param url
     * @param urlParams
     * @param headParams
     * @param respType
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Map<String, String> urlParams, MultiValueMap<String, String> headParams, Class<T> respType) {
        return get(url, urlParams, headParams, new ParameterizedTypeReference<T>() {
            @Override
            public Type getType() {
                return respType;
            }
        });
    }

    public static <T> T get(String url, Map<String, String> urlParams, MultiValueMap<String, String> headParams, ParameterizedTypeReference<T> respTypeParam){
        headParams = addContentTypeHeader(headParams);
        RequestEntity entity = new RequestEntity(null,headParams, HttpMethod.GET, URI.create(makeUrlWithParams(url, urlParams)));

        return doExchange(entity, respTypeParam);
    }

    /**
     * 最基础的post请求
     * @param url
     * @param bodyParam
     * @param respType
     * @param <P>
     * @param <T>
     * @return
     */
    public static <P, T> T post(String url, P bodyParam, Class<T> respType) {
        return post(url, bodyParam, new ParameterizedTypeReference<T>(){
            @Override
            public Type getType() {
                return respType;
            }
        });
    }

    public static <P, T> T post(String url, P bodyParam, ParameterizedTypeReference<T> respTypeParam) {
        return post(url, null, bodyParam, respTypeParam);
    }

    /**
     * 提供url参数的Post请求
     * @param url
     * @param urlParams
     * @param bodyParam
     * @param respType
     * @param <P>
     * @param <T>
     * @return
     */
    public static <P,T> T post(String url, Map<String, String> urlParams, P bodyParam, Class<T> respType){
        return post(url, urlParams, bodyParam, new ParameterizedTypeReference<T>(){
            @Override
            public Type getType() {
                return respType;
            }
        });
    }

    public static <P,T> T post(String url, Map<String, String> urlParams, P bodyParam, ParameterizedTypeReference<T> respTypeParam){
        return post(url, urlParams, null, bodyParam, respTypeParam);
    }

    /**
     * 提供头参数和url参数的post请求
     * @param url
     * @param urlParams
     * @param headParams
     * @param bodyParam
     * @param respType
     * @param <P>
     * @param <T>
     * @return
     */
    public static <P, T> T post(String url, Map<String, String> urlParams, MultiValueMap<String, String> headParams, P bodyParam, Class<T> respType){
        return post(url, urlParams, headParams, bodyParam, new ParameterizedTypeReference<T>() {
            @Override
            public Type getType() {
                return respType;
            }
        });
    }

    public static <P, T> T post(String url, Map<String, String> urlParams, MultiValueMap<String, String> headParams, P bodyParam, ParameterizedTypeReference<T> respTypeParam){
        headParams = addContentTypeHeader(headParams);
        RequestEntity entity = new RequestEntity(bodyParam, headParams, HttpMethod.POST, URI.create(makeUrlWithParams(url, urlParams)));
        return doExchange(entity, respTypeParam);
    }

    /**
     * 增加必要的头字段{@link HttpHeaders#CONTENT_TYPE}
     * @param headParams
     */
    private static MultiValueMap<String, String> addContentTypeHeader(MultiValueMap<String, String> headParams){
        if(headParams == null){
            headParams = new LinkedMultiValueMap<>(1);
        }
        // Content-Type字段设置为application/json;
        headParams.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headParams;
    }

    /**
     * 发起http请求。从http请求结果提取请求结果
     * @param requestEntity
     * @param respTypeParam
     * @param <T>
     * @return
     */
    private static <T> T doExchange(RequestEntity requestEntity, ParameterizedTypeReference<T> respTypeParam){
        ResponseEntity<T> respEntity = getRestTemplate().exchange(requestEntity, respTypeParam);
        if(respEntity == null || respEntity.getBody() == null){
            LOGGER.info("respentity : {} ", respEntity);
            return null;
        }
        return respEntity.getBody();
    }
}
