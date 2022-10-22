package com.yz.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-15:37
 * @Description: com.yz.serviceverificationcode.controller
 * @version: 1.0
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "service-verification";
    }
}
