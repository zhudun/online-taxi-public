package com.yz.servicedriveruser.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/12/8-10:59
 * @Description: com.yz.servicedriveruser.service
 * @version: 1.0
 */
@Service
public class CityDriverUserService {
    @Autowired
    DriverUserMapper driverUserMapper;

    public ResponseResult<Boolean> isAvailableDriver(String cityCode){
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if(i>0){
            return ResponseResult.success(true);
        }else{
            return ResponseResult.success(false);
        }
    }
}
