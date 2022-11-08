package com.yz.servicedriveruser.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-5:08
 * @Description: com.yz.servicedriveruser.remote
 * @version: 1.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name );


}
