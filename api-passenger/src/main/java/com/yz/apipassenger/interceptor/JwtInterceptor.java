package com.yz.apipassenger.interceptor;

import com.yz.internalcommon.constant.TokenConstants;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.dto.TokenResult;
import com.yz.internalcommon.util.JwtUtils;
import com.yz.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-17:53
 * @Description: com.yz.apipassenger.interceptor
 * @version: 1.0
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        //解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);


        if(tokenResult == null){
            resultString = "token invalid";
            result = false;
        }else{
            //拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            //从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if(StringUtils.isBlank(tokenRedis)||!token.trim().equals(tokenRedis.trim())){
                resultString = "token invalid";
                result = false;
            }
        }


        if(!result){
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }


        return result;
    }
}
