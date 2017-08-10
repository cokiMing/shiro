package com.shiro.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

/**
 * Created by wuyiming on 2017/8/8.
 */
@Repository
public class RedisDao {
    @Autowired
    private JedisPool jedisPool;

    public void set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(SafeEncoder.encode(key),SafeEncoder.encode(value));
        }finally {
            jedis.close();
        }
    }

    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] value = jedis.get(SafeEncoder.encode(key));
            return SafeEncoder.encode(value);
        }finally {
            jedis.close();
        }
    }

    public long incr(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.incr(SafeEncoder.encode(key));
        }finally {
            jedis.close();
        }
    }

    public void del(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }
}
