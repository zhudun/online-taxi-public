package com.yz.apiboss.clients;

import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.security.util.ManifestEntryVerifier;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-15:29
 * @Description: com.yz.apiboss.clients
 * @version: 1.0
 */
@FeignClient("service-driver-user")
public interface DriverUserCilent {

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.PUT,value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);


}
