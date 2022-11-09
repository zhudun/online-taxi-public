package com.yz.apidriver.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-10:06
 * @Description: com.yz.apidriver.remote
 * @version: 1.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest);

}
