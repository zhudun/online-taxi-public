package com.yz.servicemap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-17:20
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "service map...";
    }
}
