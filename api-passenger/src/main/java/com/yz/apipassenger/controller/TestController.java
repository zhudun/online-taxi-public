package com.yz.apipassenger.controller;

import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/21-18:33
 * @Description: com.yz.apipassenger.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "test api-passenger";
    }

    /**
     * 需要有token
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult  authTest(){
        return ResponseResult.success("auth test");
    }

    /**
     * 不需要有token也能正常返回
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult  noauthTest(){
        return ResponseResult.success("noauth test");
    }
}
