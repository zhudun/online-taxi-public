package com.yz.apipassenger.controller;

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
}
