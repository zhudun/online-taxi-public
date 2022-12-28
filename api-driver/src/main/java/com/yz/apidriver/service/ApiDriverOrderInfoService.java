package com.yz.apidriver.service;

import com.yz.apidriver.remote.ServiceOrderClient;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: yangzhen
 * @Date 2022/12/28-21:51
 * @Description: com.yz.apidriver.service
 * @version: 1.0
 */
@Service
public class ApiDriverOrderInfoService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest){
        return serviceOrderClient.toPickUpPassenger(orderRequest);
    }

}

