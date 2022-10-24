package com.yz.servicepassengeruser.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.VerificationCodeDTO;
import com.yz.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-23:32
 * @Description: com.yz.servicepassengeruser.controller
 * @version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号：" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{passengerPhone}")
    public ResponseResult getUser(@PathVariable("passengerPhone") String passengerPhone) {

        System.out.println("service-passenger-user:phone:" + passengerPhone);
        return userService.getUserByPhone(passengerPhone);
    }


}
