package com.huangtl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class RedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key ,byte[] value){
        Jedis jedis = getJedis();
        try {
            jedis.set(key,value);
            return value;
        } finally {
            jedisPool.close();
        }
    }
    public void expire(byte[] key,int i){
        Jedis jedis = getJedis();
        try {
            jedis.expire(key,i);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String keyPrefix){
        Jedis jedis = getJedis();
        try {
            return jedis.keys((keyPrefix+"*").getBytes());
        }finally{
            jedis.close();
        }
    }


}
