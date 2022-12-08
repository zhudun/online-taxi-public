package com.yz.serviceorder.remote;

import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangzhen
 * @Date 2022/12/8-11:24
 * @Description: com.yz.serviceorder.remote
 * @version: 1.0
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @GetMapping("/city-driver/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam String cityCode);



}
