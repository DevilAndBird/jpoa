package com.fh.util;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
  
/** 
 * redis 
 * @author sunqp  tangqm
 * 
 */  
public class RedisUtil {  
      
    private static Logger logger = Logger.getLogger(RedisUtil.class);  
      
    private static RedisTemplate<Serializable, Object> redisTemplate;  
      
    /** 
     * 写入或更新缓存 
     * @param key 
     * @param value 
     * @return 
     */  
    public static boolean set(final String key, Object value)  
    {  
        boolean result = false;  
        try {  
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();  
            operations.set(key, value);  
            result = true;  
        } catch (Exception e) {  
            logger.error("write redis is faill");  
            e.printStackTrace();  
        }
        return result;  
    }  
      
    /**  
     * 写入缓存  
     *  设置失效时间 
     * @param key  
     * @param value  
     * @return  
     */    
    public static boolean set(final String key, Object value, Long expireTime) {    
        boolean result = false;    
        try {    
            ValueOperations<Serializable, Object> operations = redisTemplate    
                    .opsForValue();    
            operations.set(key, value);    
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);    
            result = true;    
        } catch (Exception e) {    
            e.printStackTrace();    
        }
        return result;    
    }
    
    /** 
     * 读取缓存 
     * @param key 
     * @return 
     */  
    public static Object get(final String key)  {  
        Object result = null;  
        ValueOperations<Serializable, Object> operations = redisTemplate  
                .opsForValue();  
        try {
        	result = operations.get(key); 
		} catch (Exception e) {
			logger.error( "redis服务开启异常");
		}
        return result;  
    }  
      
    /** 
     * 删除对应的value 
     * @param key 
     */  
    public static void remove(final String key)  
    {  
        if (exists(key)) {  
            redisTemplate.delete(key);  
        }  
    }  
      
    /** 
     * 批量删除对应的value 
     *  
     * @param keys 
     */  
    public static void remove(final String... keys) {  
        for (String key : keys) {  
            remove(key);  
        }  
    }  
      
    /** 
     * 批量删除key 
     *  
     * @param pattern 正则表达式 
     */  
    public static void removePattern(final String pattern) {  
        Set<Serializable> keys = redisTemplate.keys(pattern);  
        if (keys.size() > 0)  
            redisTemplate.delete(keys);  
    }  
      
    /** 
     * 判断缓存中是否有对应的value 
     *  
     * @param key 
     * @return 
     */  
    public static boolean exists(final String key) {  
        return redisTemplate.hasKey(key);  
    }  
  
    public  void setRedisTemplate(  
            RedisTemplate<Serializable, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }  
}
