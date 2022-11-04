package com.yz.apiboss.service;

import com.yz.apiboss.clients.DriverUserCilent;
import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-15:25
 * @Description: com.yz.apiboss.service
 * @version: 1.0
 */
@Service
public class DriverUserService {

    @Autowired
    private DriverUserCilent driverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser){
        return driverUserClient.addDriverUser(driverUser);
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        return driverUserClient.updateDriverUser(driverUser);
    }
}
