package com.yz.servicedriveruser.controller;

import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.mapper.DriverUserMapper;
import com.yz.servicedriveruser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/1-12:37
 * @Description: com.yz.servicedriveruser.controller
 * @version: 1.0
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser){
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }




}
