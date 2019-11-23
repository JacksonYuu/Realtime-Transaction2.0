package com.itcast.dao.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisService<K, V> implements IRedisService<K, V> {

    @Resource
    private RedisTemplate<K, V> redisTemplate;

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(final K key, final V value, final long expiredTime) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        if (expiredTime <= 0) {
            valueOper.set(value);
        } else {
            valueOper.set(value, expiredTime, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public V get(final K key) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        return valueOper.get();
    }

    @Override
    public Object getHash(K key, String name) {
        Object res = redisTemplate.boundHashOps(key).get(name);
        return res;
    }

    @Override
    public void del(K key) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}
