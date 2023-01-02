package com.yz.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yz.internalcommon.dto.TokenResult;

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

    private static final String JWT_KEY_PHONE = "Phone";

    //乘客是1，司机是2
    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";


    //生成token
    public static String generatorToken(String phone,String identity,String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,phone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        //token过期时间
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach((k,v) -> {
            builder.withClaim(k, v);
        });
        //整合过期时间
//        builder.withExpiresAt(time);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;

    }
    //解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }


    /**
     * 校验token，主要判断token是否异常
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult= JwtUtils.parseToken(token);
        } catch (Exception e){

        }
        return tokenResult;
    }


    public static void main(String[] args) {

        String s = generatorToken("16619847595","1","accessToken");
        System.out.println("生成的token是 = " + s);

        TokenResult tokenResult = parseToken(s);

        System.out.println("解析后的手机号："+tokenResult.getPhone());
        System.out.println("解析后的身份："+tokenResult.getIdentity());
    }
}
