package com.itcast.dao.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试
 */
public class temp {

    @Test
    public void test() throws InterruptedException {

        RedisService redisService = RedisTool.getRedisService();

//        System.out.println(redisService.get("9-15:2019-11-22 18:19:56"));
        HashMap<String, String> allMessage = redisService.scan("*");

        for (Map.Entry m : allMessage.entrySet()) {

            System.out.println(m.getKey() + ":" + m.getValue());
        }

//        redisService.set("ab", "11", 5000);
//
//        redisService.set("abc", "11", 5000);
//
//        HashMap<String, String> allMessage = redisService.scan("a*");
//
//        for (Map.Entry<String, String> s : allMessage.entrySet()) {
//
//            System.out.println(s.getKey() + ":" + s.getValue());
//        }
//
//        Thread.sleep(5000);
    }

    @Test
    public void second() {

        while (true) {

            RedisService redisService = RedisTool.getRedisService();

            System.out.println(redisService.get("a"));
        }
    }
}
