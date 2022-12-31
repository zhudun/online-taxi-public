package com.yz.servicepay.service;

import com.yz.internalcommon.request.OrderRequest;
import com.yz.servicepay.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/12/31-19:54
 * @Description: com.yz.servicepay.service
 * @version: 1.0
 */
@Service
public class AlipayService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public void pay(Long orderId){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);
        serviceOrderClient.pay(orderRequest);

    }
}
