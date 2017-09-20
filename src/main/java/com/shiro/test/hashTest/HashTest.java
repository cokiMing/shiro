package com.shiro.test.hashTest;

import com.shiro.common.util.KeyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyiming on 2017/8/25.
 */
public class HashTest {

    public static void main(String[] args){
        Map<Integer,Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 10000; i++){
            String key = KeyUtils.uuid();
            int hashCode = Math.abs(key.hashCode());
            int num = hashCode % 15;
            Integer integer = hashMap.get(num);
            if (integer == null){
                integer = 0;
            }
            integer ++;
            hashMap.put(num,integer);
        }

        System.out.println(hashMap);
    }
}
