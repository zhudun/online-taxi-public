package com.yz.apipassenger.controller;

import com.yz.apipassenger.service.UserService;
import com.yz.internalcommon.dto.ResponseResult;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yangzhen
 * @Date 2022/10/24-21:19
 * @Description: com.yz.apipassenger.controller
 * @version: 1.0
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){
        //从http请求中获取accessToken
        String accessToken = request.getHeader("Authorization");


        return userService.getUserByAccessToken(accessToken);

    }

}
