package com.yz.servicedriveruser.service;

import com.yz.internalcommon.dto.Car;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TerminalResponse;
import com.yz.servicedriveruser.mapper.CarMapper;
import com.yz.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-18:00
 * @Description: com.yz.servicedriveruser.service
 * @version: 1.0
 */
@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car){
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        // 获得此车辆的终端id：tid
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo());
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        carMapper.insert(car);
        return ResponseResult.success("");
    }
}
