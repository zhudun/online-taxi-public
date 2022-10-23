package com.yz.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-16:34
 * @Description: com.yz.internalcommon.util
 * @version: 1.0
 */
public class JwtUtils {

    //盐
    private static final String SIGN = "YANGyz!@#$";

    //生成token
    public static String generatorToken(Map<String,String> map){
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date time = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach((k,v) -> {
            builder.withClaim(k, v);
        });
        //整合过期时间
        builder.withExpiresAt(time);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;

    }
    //解析token


    public static void main(String[] args) {
        Map<String,String> map  = new HashMap<>();
        map.put("name","zhansan");
        map.put("age","14");
        String s = generatorToken(map);
        System.out.println("生成的token是 = " + s);
    }
}
