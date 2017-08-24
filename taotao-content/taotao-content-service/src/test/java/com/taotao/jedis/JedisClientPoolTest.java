package com.taotao.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017-8-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)//基于Junit4的Spring测试框架
@ContextConfiguration({"classpath:spring/applicationContext-redis.xml"})//启动Spring容器
public class JedisClientPoolTest {
    @Autowired
    JedisClient jedisClient;
    @Test
    public void testJedisClientPool() throws Exception {
        //初期化spring容器
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
//        //从容器中获得JedisClient对象
//        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        //使用JedisClient对象操作redis
        jedisClient.set("jedisClient","aaaaa");
        String ss = jedisClient.get("jedisClient");
        System.out.println(ss);
    }
}