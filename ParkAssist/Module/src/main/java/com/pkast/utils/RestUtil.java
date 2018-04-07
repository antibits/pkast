package com.pkast.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class RestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    /**
     * @see #getRestTemplate()
     */
    private static RestTemplate restTemplate = null;

    public static <K, V, M extends Map<K, V>> String makeUrlWithParams(String url, M params){
        StringBuilder sb = new StringBuilder(url);
        if(!CollectionUtil.isEmpty(params)){
            sb.append("?");
        }
        params.entrySet().forEach(paramEntry->{
            sb.append(paramEntry.getValue()).append("=").append(paramEntry.getValue()).append("&");
        });

        return sb.substring(0, sb.length() - 1);
    }

    private static synchronized RestTemplate getRestTemplate(){
        if(restTemplate == null){
            restTemplate = new RestTemplate();

            restTemplate.setMessageConverters(Arrays.asList(BeanUtil.getBean("jsonConverter")));
        }
        return restTemplate;
    }

    /**
     * get 传参全部通过url。
     * @param url
     * @param params
     * @param respType
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Map<String, String> params, Class<T> respType) {
        HttpHeaders headers = new HttpHeaders();
        return getRestTemplate().getForObject(makeUrlWithParams(url, params), respType);
    }

    public static <P, T> T post(String url, P param, Class<T> respType) {
        return getRestTemplate().postForObject(url, param, respType);
    }
}
