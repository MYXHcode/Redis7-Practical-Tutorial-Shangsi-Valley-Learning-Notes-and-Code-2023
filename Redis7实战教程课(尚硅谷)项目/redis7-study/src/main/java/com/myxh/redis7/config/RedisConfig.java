package com.myxh.redis7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author MYXH
 * @date 2023/11/28
 */
@Configuration
public class RedisConfig
{
    /**
     * Redis 序列化的工具配置类，下面这个请一定开启配置
     * 127.0.0.1:6379> keys *
     * 1、"ord:101"    序列化过
     * 2、"\xac\xed\x00\x05t\x00\aord:101"    没有序列化过
     * this.redisTemplate.opsForValue();     // 提供了操作 string 类型的所有方法
     * this.redisTemplate.opsForList();    // 提供了操作 list 类型的所有方法
     * this.redisTemplate.opsForSet();    // 提供了操作 set 的所有方法
     * this.redisTemplate.opsForHash();    // 提供了操作 hash 表的所有方法
     * this.redisTemplate.opsForZSet();    // 提供了操作 zset 的所有方法
     *
     * @param lettuceConnectionFactory lettuce 连接工厂
     * @return redisTemplate redis 模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory)
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // 设置 key 序列化方式string
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置 value 的序列化方式 json，使用 GenericJackson2JsonRedisSerializer 替换默认序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
