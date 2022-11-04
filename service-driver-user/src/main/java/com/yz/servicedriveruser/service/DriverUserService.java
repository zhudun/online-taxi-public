package com.yz.servicedriveruser.service;

import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date 2022/10/28-15:56
 * @Description: com.yz.servicedriveruser.service
 * @version: 1.0
 */
@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;


    public ResponseResult testGetDriverUser(){
        DriverUser driverUser = driverUserMapper.selectById("1");
        return ResponseResult.success(driverUser);
    }

    public ResponseResult addDriverUser(DriverUser driverUser){
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);
        return ResponseResult.success("");
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success("");
    }


}
