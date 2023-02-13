package com.rtq.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author rtq
 * @Date 2023/2/1
 **/
public class PhoneCode {
    public static void main(String[] args) {

        verifyCode("18569868162");
//        String result = result("18569868162", "552687");
//        System.out.println(result);

    }
    //3,验证码校验
    public static String result(String phone,String code){
        //连接
        Jedis jedis = new Jedis("47.95.7.116",6379);
        jedis.auth("1128");
        String codeKey="VerifyCode"+phone+":code";
        String codes = jedis.get(codeKey);
        if (code.equals(codes)){
            jedis.close();
            return "正确";

        }else {
            jedis.close();
            return "错误";
        }

    }


    //2,每个手机每天只能发送三次验证码，将验证码存入redis设置过期时间2分钟
    public static void verifyCode(String phone){
        //连接
        Jedis jedis = new Jedis("47.95.7.116",6379);
        jedis.auth("1128");
        //拼接key
        //手机发送次数key
        String countKey="VerifyCode"+phone+":count";
        //验证码key
        String codeKey="VerifyCode"+phone+":code";

        //每个手机每天只能发送3次
        String count = jedis.get(countKey);
        if (count==null){
            //没有发送次数，第一次发送
            //设置发送次数为1
            System.out.println("发送成功，还剩"+(3-Integer.parseInt(count))+"次数");
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)<=2){
            //发送次数+1
            System.out.println("发送成功，还剩"+(3-Integer.parseInt(count))+"次数");
            jedis.incr(countKey);
        }else if (Integer.parseInt(count)>2){
            System.out.println("今天发送次数大于3次，不能发送");
            return;
            //jedis.close();
        }

        //发送验证码存放到redis
        String vcode = code();
        jedis.setex(codeKey,120,vcode);
        jedis.close();

    }


    //1,生成6位随机验证码
    public static String code(){
        Random random = new Random();

        String code="";
        for (int i = 0; i < 6; i++) {
            int nextInt = random.nextInt(10);
            code+=nextInt;
        }
        return code;
    }
}
