package com.rtq.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author rtq
 * @Date 2023/2/1
 **/
public class JedisDmo1 {
    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("47.95.7.116", 6379);
        jedis.auth("1128");

        //测试
        String value = jedis.ping();
        System.out.println(value);

    }
    //操作key
    @Test
    public void testKey(){
        //创建Jedis对象
        Jedis jedis = new Jedis("47.95.7.116", 6379);
        jedis.auth("1128");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
    @Test
    public void getKey(){
        //创建Jedis对象
        Jedis jedis = new Jedis("47.95.7.116", 6379);
        jedis.auth("1128");
        String s = jedis.get("VerifyCode18569868162:code");
        System.out.println(s);
        String s1 = jedis.get("VerifyCode18569868162:count");
        System.out.println(s1);
    }
}
