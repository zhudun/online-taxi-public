package com.yz.serviceorder.controller;


import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-11-17
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest , HttpServletRequest httpServletRequest){
            // 测试通过，通过header获取deviceCode
    //        String deviceCode = httpServletRequest.getHeader(HeaderParamConstants.DEVICE_CODE);
    //        orderRequest.setDeviceCode(deviceCode);

        log.info("service-order"+orderRequest.getAddress());
        return null;
    }

    @GetMapping("/testMapper")
    public String testMapper(){
        return orderInfoService.testMapper();
    }


}
