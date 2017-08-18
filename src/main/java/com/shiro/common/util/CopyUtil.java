package com.shiro.common.util;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.exception.InvalidParamException;
import com.shiro.entity.DO.UserDO;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * Created by wuyiming on 2017/7/25.
 */
public class CopyUtil {

    /**
     * 该模式下只有选定的参数会被拷贝
     */
    private static final int MODE_PART = 0;

    /**
     * 该模式下选定参数将不会被拷贝
     */
    private static final int MODE_EXCLUDE = 1;

    /**
     * 将指定参数拷贝到一个JSONObject类的对象中
     * <notice>浅拷贝</notice>
     * @param t 待拷贝的对象
     * @param paramSet 指定参数集合
     * @return
     */
    public static <T> JSONObject copyReferParam(T t, Set<String> paramSet){
        return copy(t,MODE_PART,paramSet);
    }

    /**
     * 将指定参数以外的参数拷贝到一个JSONObject类的对象中
     * <notice>浅拷贝</notice>
     * @param t 待拷贝的对象
     * @param paramSet 排除参数集合
     * @return
     */
    public static <T> JSONObject copyExcludeParam(T t, Set<String> paramSet){
        return copy(t,MODE_EXCLUDE,paramSet);
    }

    /**
     * 从一个JSONObject类中拷贝指定参数至实体类中
     * <notice>浅拷贝</notice>
     * @param paramSet 指定参数集合
     * @return
     */
    public static <T> T copyReferParamFromJSON(JSONObject jsonObject, Class<T> clazz, Set<String> paramSet){
        try{
            return reverseCopy(jsonObject,clazz,paramSet,MODE_PART,false);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 从一个JSONObject类中拷贝指定参数外的参数至实体类中
     * <notice>浅拷贝</notice>
     * @param paramSet 排除参数集合
     * @return
     */
    public static <T> T copyExcludeParamFromJSON(JSONObject jsonObject, Class<T> clazz, Set<String> paramSet){
        try {
            return reverseCopy(jsonObject,clazz,paramSet,MODE_EXCLUDE,false);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 从一个JSONObject类中拷贝指定参数至实体类中
     * <notice>出现空参会抛出异常<notice/>
     * <notice>浅拷贝</notice>
     * @exception InvalidParamException
     * <warn>浅拷贝</warn>
     * @param <T>
     * @return
     */
    public static <T> T copyReferParamWithValid(JSONObject jsonObject,
                                               Class<T> clazz,
                                               Set<String> paramSet) throws InvalidParamException {
        return reverseCopy(jsonObject,clazz,paramSet,MODE_PART,true);
    }

    /**
     * 从一个JSONObject类中拷贝指定参数外的参数至实体类中
     * <notice>出现空参会抛出异常<notice/>
     * <notice>浅拷贝</notice>
     * @exception InvalidParamException
     * <warn>浅拷贝</warn>
     * @param <T>
     * @return
     */
    public static <T> T copyExcludeParamWithValid(JSONObject jsonObject,
                                                 Class<T> clazz,
                                                 Set<String> paramSet) throws InvalidParamException {
        return reverseCopy(jsonObject,clazz,paramSet,MODE_EXCLUDE,true);
    }

    /**
     * 深拷贝
     * @param t 待拷贝的对象
     * @param <T> 对象返回类型
     * @return
     */
    public static <T> T deepCopy(T t){
        try{
            Class<?> paramType = t.getClass();
            String paramTypeName = paramType.getName();
            if (paramType.isArray()){
                Class<?> componentType = paramType.getComponentType();
                Object result = Array.newInstance(componentType, Array.getLength(t));
                if (componentType.isPrimitive()){
                    return t;
                }

                for (int i=0;i<Array.getLength(t);i++){
                    Object object = Array.get(t, i);
                    Object cast = componentType.cast(object);
                    cast = deepCopy(cast);
                    Array.set(result,i,cast);
                }
                return (T)result;
            }
            if (!paramTypeName.startsWith("java") && !paramType.isPrimitive()){
                List<Method> methods = getMethods(t.getClass());
                Map<String,Object> paramMap = new HashMap<>();

                for (Method method : methods){
                    String name = method.getName();
                    if (name.startsWith("get")){
                        String paramName = name.substring(3);
                        Object value = method.invoke(t);
                        if (value != null){
                            value = deepCopy(value);
                        }
                        paramMap.put(paramName,value);
                    }
                }

                T result = (T)paramType.newInstance();
                for (Method method : methods){
                    String name = method.getName();
                    if (name.startsWith("set")){
                        String paramName = name.substring(3);
                        Object value = paramMap.get(paramName);
                        method.invoke(result,value);
                    }
                }
                return result;
            }

            if (t instanceof Collection){
                T list = (T)paramType.newInstance();
                for (Object object: (Collection)t){
                    object = deepCopy(object);
                    ((Collection)list).add(object);
                }
                return list;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return t;
    }

    private static <T> JSONObject copy(T t, int mode, Set<String> paramSet){
        List<Method> methods = getMethods(t.getClass());
        JSONObject result = new JSONObject();
        paramSet = paramSet == null?new HashSet<String>():paramSet;

        for (Method method : methods){
            String methodName = method.getName();
            if (methodName.startsWith("get")){
                String param = getParamName(methodName);
                if (mode == MODE_PART && paramSet.contains(param)){
                    putParamToJson(method,t,result,param);
                }
                if (mode == MODE_EXCLUDE && !paramSet.contains(param)){
                    putParamToJson(method,t,result,param);
                }
            }
        }

        return result;
    }

    private static <T> T reverseCopy(JSONObject jsonObject,
                                     Class<T> clazz,
                                     Set<String> paramSet,
                                     int mode,
                                     boolean valid)throws InvalidParamException{
        paramSet = paramSet == null?new HashSet<String>():paramSet;
        T t;
        try{
            t = clazz.newInstance();
        }catch (Exception e){
            throw new RuntimeException("create instant fail!");
        }

        List<Method> methods = getMethods(clazz);
        for (Method method : methods){
            String methodName = method.getName();
            if (methodName.startsWith("set")){
                String param = getParamName(methodName);
                if (mode == MODE_PART && paramSet.contains(param)){
                   if (valid) putParamFromJsonWithValidation(method,t,jsonObject,param);
                   else putParamFromJson(method,t,jsonObject,param);
                }
                if (mode == MODE_EXCLUDE && !paramSet.contains(param)){
                    if (valid) putParamFromJsonWithValidation(method,t,jsonObject,param);
                    else putParamFromJson(method,t,jsonObject,param);
                }
            }
        }

        return t;
    }

    private static String getParamName(String methodName){
        String paramName = methodName.substring(3);
        String firstLetter = paramName.substring(0,1);
        return paramName.replaceFirst(firstLetter,firstLetter.toLowerCase());
    }

    private static List<Method> getMethods(Class<?> clazz){
        List<Method> methods = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        methods.addAll(Arrays.asList(declaredMethods));
        String superName = clazz.getSuperclass().getName();
        if (!superName.startsWith("java")) methods.addAll(getMethods(clazz.getSuperclass()));

        return methods;
    }

    private static void putParamToJson(Method method, Object object, JSONObject jsonObject, String param){
        try{
            Object invoke = method.invoke(object);
            if (invoke != null) jsonObject.put(param,invoke);
        }catch (Exception e){
            throw new RuntimeException("get method invalid!");
        }
    }

    private static void putParamFromJson(Method method, Object object, JSONObject jsonObject, String param){
        try{
            Object o = jsonObject.get(param);
            if (o != null) method.invoke(object,o);
        }catch (Exception e){
            throw new RuntimeException("set method invalid!");
        }
    }

    private static void putParamFromJsonWithValidation(Method method,
                                                       Object object,
                                                       JSONObject jsonObject,
                                                       String param)throws InvalidParamException{
        try{
            Object o = jsonObject.get(param);
            if (o != null) method.invoke(object,o);
            else throw new Exception();
        }catch (Exception e){
            throw new InvalidParamException(param);
        }
    }

    public static void main(String[] args){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","12345678");
        jsonObject.put("password","134gdsdfsdf");
        jsonObject.put("tel","19283298412");
        jsonObject.put("name","asd");

        Set<String> set = new HashSet<>();
        set.add("tel");
        try{
            long millis = System.currentTimeMillis();
            UserDO userDO = copyExcludeParamFromJSON(jsonObject, UserDO.class, set);
            System.out.println("耗时：" + (System.currentTimeMillis() - millis));
            System.out.println(userDO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
