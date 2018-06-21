package com.huangtl.cache;

import com.huangtl.utils.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义redis缓存,存储的是用户的角色和权限信息。key是userName，value是AuthorizationInfo对象
 * 登录后第一次请求时会根据自定义{@link com.huangtl.realm.CustomRealm realm}的{@link com.huangtl.realm.CustomRealm#doGetAuthorizationInfo}方法读取角色权限信息，返回用户的AuthorizationInfo对象
 * <h3>在不同事件发生时会调用本类的某些方法，如下：</h3>
 * 登录后第一次请求时该AuthorizationInfo对象会被本类put方法调用，保存在缓存中，key为userName
 * 第一次请求后面的所有请求都会去缓存中读取AuthorizationInfo对象角色权限信息，调用本类的get方法
 * 在调用{@code subject.logout()}退出时会调用本类的remove方法删除该登录用户的AuthorizationInfo对象缓存，key为userName
 */
@Component
public class RedisCache implements Cache {

    @Autowired
    private RedisUtil redisUtil;

    private static final String _KEY_PREFIX = "SHIRO_CACHE_";

    private byte[] getKey(Object k){
        return (_KEY_PREFIX+k).getBytes();
    }

    @Override
    public Object get(Object k) throws CacheException {
        byte[] value = redisUtil.get(getKey(k));
        if(value!=null){
            return SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        return redisUtil.set(getKey(k),SerializationUtils.serialize(v));
    }

    @Override
    public Object remove(Object k) throws CacheException {
        Object v = get(k);
        redisUtil.del(getKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisUtil.clear(_KEY_PREFIX);
    }

    @Override
    public int size() {
        return redisUtil.getSizes(_KEY_PREFIX);
    }

    @Override
    public Set keys() {
        return redisUtil.keys(_KEY_PREFIX);
    }

    @Override
    public Collection values() {
        return redisUtil.values(_KEY_PREFIX);
    }
}
