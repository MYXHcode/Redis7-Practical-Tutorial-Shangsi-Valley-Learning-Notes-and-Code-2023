package com.myxh.redis7.demo;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MYXH
 * @date 2023/11/28
 */
public class LettuceDemo
{
    public static void main(String[] args)
    {
        // 1、使用构建器链式编程 RedisURI.Builder
        RedisURI redisURI = RedisURI.Builder
                .redis("192.168.199.133")
                .withPort(6379)
                .withAuthentication("default", "520.ILY!")
                .build();

        // 2、创建 Redis 连接客户端
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> redisConnection = redisClient.connect();

        // 3、通过 redisConnection 创建操作的 redisCommands
        RedisCommands<String, String> redisCommands = redisConnection.sync();

        String ping = redisCommands.ping();
        System.out.println("ping = " + ping);

        // keys
        System.out.println("---------- keys ----------");
        List<String> keys = redisCommands.keys("*");
        System.out.println("keys = " + keys);

        for (String key : keys)
        {
            System.out.println("key = " + key);
        }

        System.out.println("redisCommands.exists =>" + redisCommands.exists("k1"));
        long ttlK1 = redisCommands.ttl("k1");
        System.out.println("ttlK1 = " + ttlK1);

        // string
        System.out.println("---------- string ----------");
        redisCommands.set("k20", "hello lettuce");
        String k20 = redisCommands.get("k20");
        System.out.println("k20 = " + k20);
        long ttlK20 = redisCommands.ttl("k20");
        System.out.println("ttlK20 = " + ttlK20);
        redisCommands.expire("k20", 20L);

        Map<String, String> map = new HashMap<>();
        map.put("k30", "v30");
        map.put("k40", "v40");
        map.put("k50", "v50");
        map.put("k60", "v60");
        redisCommands.mset(map);
        System.out.println(redisCommands.mget("k30", "k40", "k50", "k60"));

        // list
        System.out.println("---------- list ----------");
        redisCommands.lpush("list10", "1", "2", "3", "4", "5");
        List<String> list = redisCommands.lrange("list10", 0, -1);

        for (String element : list)
        {
            System.out.println("element = " + element);
        }

        // set
        System.out.println("---------- set ----------");
        redisCommands.sadd("orders", "jd001");
        redisCommands.sadd("orders", "jd002");
        redisCommands.sadd("orders", "jd003");

        Set<String> orders = redisCommands.smembers("orders");

        for (String order : orders)
        {
            System.out.println(order);
        }

        redisCommands.srem("orders", "jd003");
        System.out.println(redisCommands.smembers("orders").size());

        // hash
        System.out.println("---------- hash ----------");
        redisCommands.hset("hash1", "userName", "MYXH");
        System.out.println(redisCommands.hget("hash1", "userName"));
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("telephone", "18812612826");
        hashMap.put("address", "天津科技大学");
        hashMap.put("email", "1735350920@qq.com");
        redisCommands.hmset("hash2", hashMap);
        List<KeyValue<String, String>> result = redisCommands.hmget("hash2", "telephone", "email");

        for (KeyValue<String, String> element : result)
        {
            System.out.println("element = " + element);
        }

        // zset
        System.out.println("---------- zset ----------");
        redisCommands.zadd("zset01", 60d, "v1");
        redisCommands.zadd("zset01", 70d, "v2");
        redisCommands.zadd("zset01", 80d, "v3");
        redisCommands.zadd("zset01", 90d, "v4");

        List<String> zset01 = redisCommands.zrange("zset01", 0, -1);
        zset01.forEach(System.out::println);

        // 4、关闭释放资源
        redisConnection.close();
        redisClient.shutdown();
    }
}
