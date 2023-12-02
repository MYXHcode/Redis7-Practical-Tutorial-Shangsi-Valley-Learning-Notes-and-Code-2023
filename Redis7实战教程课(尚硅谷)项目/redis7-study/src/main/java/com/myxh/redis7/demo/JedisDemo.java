package com.myxh.redis7.demo;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MYXH
 * @date 2023/11/28
 */
public class JedisDemo
{
    public static void main(String[] args)
    {
        // 1、connection 获得 jedis，通过指定 ip 和端口号
        Jedis jedis = new Jedis("192.168.199.133", 6379);

        // 2、指定访 Redis 服务服务器的密码
        jedis.auth("520.ILY!");

        // 3、获得了 jedis 客户端，可以像 jdbc 一样，访问 Redis
        String ping = jedis.ping();
        System.out.println("ping = " + ping);

        // keys
        System.out.println("---------- keys ----------");
        Set<String> keys = jedis.keys("*");
        System.out.println("keys = " + keys);

        for (String key : keys)
        {
            System.out.println("key = " + key);
        }

        System.out.println("jedis.exists =>" + jedis.exists("k1"));
        long ttlK1 = jedis.ttl("k1");
        System.out.println("ttlK1 = " + ttlK1);

        // string
        System.out.println("---------- string ----------");
        jedis.set("k10", "hello jedis");
        String k10 = jedis.get("k10");
        System.out.println("k10 = " + k10);
        long ttlK10 = jedis.ttl("k10");
        System.out.println("ttlK10 = " + ttlK10);
        jedis.expire("k10", 20L);

        jedis.mset("k20", "v20", "k30", "v30", "k40", "v40", "k50", "v50", "k60", "v60");
        System.out.println(jedis.mget("k10", "k20", "k30", "k40", "k50", "k60"));

        // list
        System.out.println("---------- list ----------");
        jedis.lpush("list10", "1", "2", "3", "4", "5");
        List<String> list = jedis.lrange("list10", 0, -1);

        for (String element : list)
        {
            System.out.println("element = " + element);
        }

        // set
        System.out.println("---------- set ----------");
        jedis.sadd("orders", "jd001");
        jedis.sadd("orders", "jd002");
        jedis.sadd("orders", "jd003");

        Set<String> orders = jedis.smembers("orders");

        for (String order : orders)
        {
            System.out.println(order);
        }

        jedis.srem("orders", "jd003");
        System.out.println(jedis.smembers("orders").size());

        // hash
        System.out.println("---------- hash ----------");
        jedis.hset("hash1", "userName", "MYXH");
        System.out.println(jedis.hget("hash1", "userName"));
        Map<String, String> map = new HashMap<>();
        map.put("telephone", "18812612826");
        map.put("address", "天津科技大学");
        map.put("email", "1735350920@qq.com");
        jedis.hmset("hash2", map);
        List<String> result = jedis.hmget("hash2", "telephone", "email");

        for (String element : result)
        {
            System.out.println("element = " + element);
        }

        // zset
        System.out.println("---------- zset ----------");
        jedis.zadd("zset01", 60d, "v1");
        jedis.zadd("zset01", 70d, "v2");
        jedis.zadd("zset01", 80d, "v3");
        jedis.zadd("zset01", 90d, "v4");

        List<String> zset01 = jedis.zrange("zset01", 0, -1);
        zset01.forEach(System.out::println);
    }
}
