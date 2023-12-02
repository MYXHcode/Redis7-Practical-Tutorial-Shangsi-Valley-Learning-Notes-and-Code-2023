package com.myxh.redis7.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author MYXH
 * @date 2023/11/28
 */
@Slf4j
@Service
public class OrderService
{
    public static final String ORDER_KEY = "ord:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /*
    @Resource
    private StringRedisTemplate stringRedisTemplate;
     */

    public void addOrder()
    {
        int keyId = ThreadLocalRandom.current().nextInt(1000) + 1;
        String serialNo = UUID.randomUUID().toString();

        String key = ORDER_KEY + keyId;
        String value = "京东订单" + serialNo;

        redisTemplate.opsForValue().set(key, value);
        // stringRedisTemplate.opsForValue().set(key, value);

        System.out.println("----- key:" + key + " -----");
        System.out.println("----- value:" + value + " -----");
    }

    public String getOrderById(Integer keyId)
    {
        return (String) redisTemplate.opsForValue().get(ORDER_KEY + keyId);
        // return stringRedisTemplate.opsForValue().get(ORDER_KEY + keyId);
    }
}
