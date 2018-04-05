package com.pkast.utils;

import java.util.Collection;

/**
 * 集合操作类
 */
public class CollectionUtil {
    public static <E, T extends Collection<E>> boolean isEmpty(T collection){
        return collection == null || collection.isEmpty();
    }

}
