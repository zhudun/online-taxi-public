package com.yz.servicedriveruser.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/12/8-11:06
 * @Description: com.yz.servicedriveruser.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/city-driver")
public class CItyDriverController {

    @Autowired
    CityDriverUserService cityDriverUserService;


    /**
     * 根据城市编码查询当地城市是否有可用司机
     * @param cityCode
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult isAvailableDriver(String cityCode){
        return cityDriverUserService.isAvailableDriver(cityCode);

    }
}
