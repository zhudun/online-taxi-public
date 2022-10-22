package com.yz.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-23:18
 * @Description: com.yz.servicepassengeruser.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "service-passenger-user";
    }
}
