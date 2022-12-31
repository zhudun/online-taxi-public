package com.yz.servicepay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/12/30-23:22
 * @Description: com.yz.testalipay.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){

        System.out.println("支付宝回调啦");
        return "test-alipay";
    }

}
