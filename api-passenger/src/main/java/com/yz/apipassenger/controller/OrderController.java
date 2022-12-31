package com.yz.apipassenger.controller;

import com.yz.apipassenger.service.OrderService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-19:56
 * @Description: com.yz.apipassenger.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    /**
     * 创建订单/下单
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return orderService.add(orderRequest);
    }

    /**
     * 乘客取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId){
        return orderService.cancel(orderId);
    }
}
