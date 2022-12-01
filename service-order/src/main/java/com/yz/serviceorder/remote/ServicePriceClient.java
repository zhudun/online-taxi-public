package com.yz.serviceorder.remote;

import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangzhen
 * @Date 2022/12/2-2:17
 * @Description: com.yz.serviceorder.remote
 * @version: 1.0
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);
}
