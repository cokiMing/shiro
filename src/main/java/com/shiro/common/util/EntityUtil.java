package com.shiro.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class EntityUtil {

    /**
     * 同一父类下的子类互相转换的方法
     * @param origin
     * @return
     */
    public static <T> T castToObject(Object origin,Class<T> clazz) {
        Map<String,Object> paramMap = new HashMap<>();
        Class<?> originClazz = origin.getClass();
        while (!originClazz.getName().startsWith("java")){
            Method[] declaredMethods = originClazz.getDeclaredMethods();
            for (Method method : declaredMethods){
                String methodName = method.getName();
                String paramName = methodName.substring(3);
                if (method.getName().startsWith("get")){
                    try{
                        Object param = method.invoke(origin, null);
                        paramMap.put(paramName,param);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
            originClazz = originClazz.getSuperclass();
        }

        Method[] methods = clazz.getMethods();
        T t;
        try{
            t = clazz.newInstance();
            for (Method method : methods){
                String methodName = method.getName();
                String paramName = methodName.substring(3);
                if (methodName.startsWith("set")){
                    if (paramMap.containsKey(paramName)){
                        method.invoke(t,paramMap.get(paramName));
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return t;
    }
}
