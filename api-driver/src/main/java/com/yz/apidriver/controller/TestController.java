package com.yz.apidriver.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-16:10
 * @Description: com.yz.apidriver.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    /**
     * 需要授权的接口
     * @return
     */
    @RequestMapping("/auth")
    public String testAuth() {
        return "auth";
    }

    /**
     * 不需要授权的接口
     * @return
     */
    @RequestMapping("/noauth")
    public String testNoAuth() {
        return "no  auth";
    }
}
