package com.yz.apipassenger.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-12:00
 * @Description: com.yz.apipassenger.remote
 * @version: 1.0
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
