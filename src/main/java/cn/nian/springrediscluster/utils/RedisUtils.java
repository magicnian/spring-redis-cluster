package cn.nian.springrediscluster.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private final static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 指定key的缓存时间
     *
     * @param key
     * @param time 单位秒
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time < 0)
                throw new RuntimeException("expire time must more than 0");
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("key:{} | set redis expire time get exception:{}", key, e);
        }
        return false;
    }


    /**
     * 获取key的过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除一个或多个key-value
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (key.length == 1) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete(CollectionUtils.arrayToList(key));
        }
    }

    /**
     * 普通获取获取
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通设置
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 设置并加上过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * hash获取
     *
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, Object item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash设置
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * hash设置并加上过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key, Map<Object, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        expire(key, time);
        return true;
    }

    /**
     * 往hash表中放入数据，如果hash不存在将创建
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, Object item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 删除hash表中的元素
     * @param key
     * @param item
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

}
