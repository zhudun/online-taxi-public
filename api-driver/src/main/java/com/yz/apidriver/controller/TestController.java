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

    @RequestMapping("/test")
    public String test() {
        return "api-driver";
    }
}
