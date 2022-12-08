package com.yz.serviceorder.controller;

import com.yz.internalcommon.dto.OrderInfo;
import com.yz.serviceorder.mapper.OrderInfoMapper;
import com.yz.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-20:30
 * @Description: com.yz.serviceorder
 * @version: 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "service-order";
    }

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    OrderInfoService orderInfoService;


    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId){
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.dispatchRealTimeOrder(orderInfo);
        return "test-real-time-order success" ;
    }
}
