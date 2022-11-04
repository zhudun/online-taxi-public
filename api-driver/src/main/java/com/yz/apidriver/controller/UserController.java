package com.yz.apidriver.controller;

import com.yz.apidriver.service.UserService;
import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Driver;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-17:16
 * @Description: com.yz.apidriver.controller
 * @version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return userService.updateUser(driverUser);
    }


}
