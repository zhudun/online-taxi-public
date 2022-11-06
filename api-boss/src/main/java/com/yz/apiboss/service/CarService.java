package com.yz.apiboss.service;

import com.yz.apiboss.clients.DriverUserCilent;
import com.yz.internalcommon.dto.Car;
import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/6-15:29
 * @Description: com.yz.apiboss.service
 * @version: 1.0
 */
@Service
public class CarService {

    @Autowired
    private DriverUserCilent driverUserCilent;

    public ResponseResult addCar(Car car){
        return driverUserCilent.addCar(car);
    }

}
