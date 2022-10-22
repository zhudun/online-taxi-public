package com.yz.apipassenger.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-20:05
 * @Description: com.yz.apipassenger.remote
 * @version: 1.0
 */
@FeignClient("sevice-verification")
public interface ServiceVerificationcodeClient {

    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);
}
