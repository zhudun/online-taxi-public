package com.yz.serviceorder.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yangzhen
 * @Date 2022/11/12-15:49
 * @Description: com.yz.serviceorder.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

//    @Autowired
//    OrderInfoS    ervice orderInfoService;

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
//        return orderInfoService.add(orderRequest);
        return null;
    }


}
