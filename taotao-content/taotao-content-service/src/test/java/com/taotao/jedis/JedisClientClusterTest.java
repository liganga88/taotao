package com.taotao.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017-8-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)//基于Junit4的Spring测试框架
@ContextConfiguration({"classpath:spring/applicationContext-redis.xml"})//启动Spring容器
public class JedisClientClusterTest {
    @Autowired
    private JedisClient jedisClient;

    @Test
    public void testJedisClientCluster(){
        //使用JedisClient对象操作redis
        jedisClient.set("jedisClientCluster","bbbbb");
        String ss = jedisClient.get("jedisClientCluster");
        System.out.println(ss);
    }
}