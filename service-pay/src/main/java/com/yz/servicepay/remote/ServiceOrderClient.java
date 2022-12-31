package com.yz.servicepay.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: yangzhen
 * @Date 2022/12/31-19:54
 * @Description: com.yz.servicepay.remote
 * @version: 1.0
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value = "/order/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest);
}
