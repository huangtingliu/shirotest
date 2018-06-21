package com.huangtl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.List;
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
            jedis.close();
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
    public List<byte[]> values(String keyPrefix){
        Jedis jedis = getJedis();
        try {
            Set<byte[]> keysSet = keys(keyPrefix);
            byte[][] keys = keysSet.toArray(new byte[keysSet.size()][]);
            List<byte[]> values = jedis.mget(keys);
            return values;
        }finally{
            jedis.close();
        }
    }

    public int getSizes(String keyPrefix){
        Jedis jedis = getJedis();
        try {
            return jedis.keys((keyPrefix+"*").getBytes()).size();
        }finally{
            jedis.close();
        }
    }

    public void clear(String keyPrefix){
        Jedis jedis = getJedis();
        try {
            Set<byte[]> keys = jedis.keys((keyPrefix + "*").getBytes());
            Iterator<byte[]> iterator = keys.iterator();
            while (iterator.hasNext()){
                jedis.del(iterator.next());
            }
        }finally{
            jedis.close();
        }
    }

}
