package com.yz.apidriver.controller;

import com.yz.apidriver.service.PayService;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/12/29-23:08
 * @Description: com.yz.apidriver.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    /**
     * 司机发起收款
     * @param orderId
     * @param price
     * @return
     */
    @PostMapping("/push-pay-info")
    public ResponseResult pushPayInfo(@RequestParam Long orderId , @RequestParam String price, @RequestParam Long passengerId){

        return payService.pushPayInfo(orderId,price,passengerId);
    }
}
