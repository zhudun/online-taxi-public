package com.yz.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        try {
            JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e){
            resultString = "token time out";
            result = false;
        } catch (AlgorithmMismatchException e){
            resultString = "token AlgorithmMismatchException";
            result = false;
        } catch (Exception e){
            resultString = "token invalid";
            result = false;
        }

        if(!result){
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }


        return result;
    }
}
