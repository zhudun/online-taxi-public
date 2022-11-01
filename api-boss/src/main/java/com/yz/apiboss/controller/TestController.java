package com.yz.apiboss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/1-13:10
 * @Description: com.yz.apiboss.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "api-boss";
    }
}
