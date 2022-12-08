package com.yz.servicedriveruser.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.mapper.DriverUserMapper;
import com.yz.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/28-15:25
 * @Description: com.yz.servicedriveruser.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/test")
    public String test(){
        return "service-driver-map";

    }

    @GetMapping("/testDB")
    public ResponseResult testDB(){
        return driverUserService.testGetDriverUser();

    }

    @Autowired
    DriverUserMapper driverUserMapper;

    @GetMapping("/test_xml")
    public int testXml(String cityCode){
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        return i;

    }


}
