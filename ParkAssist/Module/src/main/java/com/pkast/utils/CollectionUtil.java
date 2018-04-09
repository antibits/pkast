package com.pkast.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 集合操作类
 */
public class CollectionUtil {
    public static <E, T extends Collection<E>> boolean isEmpty(T collection){
        return collection == null || collection.isEmpty();
    }

    public static <K,V, T extends Map<K, V>> boolean isEmpty(T map){
        return map == null || map.isEmpty();
    }

    public static <K, V> Map<K, V> tinyMap(K key, V val){
        Map<K, V> map = new HashMap<>(1);
        map.put(key, val);
        return map;
    }

    public static <K,V, T extends Map<K,V>> V getMapVal(T map, K key){
        if(isEmpty(map)){
            return null;
        }
        return map.get(key);
    }
}
