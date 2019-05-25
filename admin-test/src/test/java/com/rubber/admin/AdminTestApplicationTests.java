package com.rubber.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTestApplicationTests {



    @Value("${spring.datasource.url}")
    private String tt;

    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void contextLoads() {

        jedisCluster.set("59977","12");


        String s = jedisCluster.get("59977");

        System.out.println(s);
    }

}
