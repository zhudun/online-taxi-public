package com.yz.apidriver.controller;

import com.yz.apidriver.service.UserService;
import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.DriverUserWorkStatus;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.dto.TokenResult;
import com.yz.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){

        return userService.changeWorkStatus(driverUserWorkStatus);
    }

    /**
     * 根据司机token查询 司机和车辆绑定关系
     * @param request
     * @return
     */
    @GetMapping("/driver-car-binding-relationship")
    public ResponseResult getDriverCarBindingRelationship(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(authorization);
        String driverPhone = tokenResult.getPhone();

        return userService.getDriverCarBindingRelationship(driverPhone);

    }


}
