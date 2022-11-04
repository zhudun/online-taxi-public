package com.yz.apidriver.service;

import com.yz.apidriver.remote.ServiceDriverUserClient;
import com.yz.internalcommon.dto.DriverUser;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-17:16
 * @Description: com.yz.apidriver.service
 * @version: 1.0
 */
@Service
public class UserService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;


    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }

}
