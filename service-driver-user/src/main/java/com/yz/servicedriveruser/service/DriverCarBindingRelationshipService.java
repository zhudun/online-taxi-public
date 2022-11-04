package com.yz.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.DriverCarConstants;
import com.yz.internalcommon.dto.DriverCarBindingRelationship;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-22:13
 * @Description: com.yz.servicedriveruser.service
 * @version: 1.0
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bing(DriverCarBindingRelationship driverCarBindingRelationship){
        //判断 如果司机和车辆已经做过绑定，则不允许再次绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);

        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());
        }

        //司机被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);

        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_BIND_EXISTS.getMessage());
        }

        //车辆被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);

        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if(integer.intValue()>0){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(),CommonStatusEnum.CAR_BIND_EXISTS.getMessage());
        }


        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");
    }



}
