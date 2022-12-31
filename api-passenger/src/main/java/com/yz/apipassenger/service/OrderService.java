package com.yz.apipassenger.service;

import com.yz.apipassenger.remote.ServiceOrderClient;
import com.yz.internalcommon.constant.IdentityConstants;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-19:58
 * @Description: com.yz.apipassenger.service
 * @version: 1.0
 */
@Service
public class OrderService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public ResponseResult add(OrderRequest orderRequest){
        return serviceOrderClient.add(orderRequest);
    }

    /**
     * 乘客取消订单
     * @param orderId
     * @return
     */
    public ResponseResult cancel(Long orderId){
        return serviceOrderClient.cancel(orderId, IdentityConstants.PASSENGER_IDENTITY);
    }
}
