package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-17:27
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class DirectionService {

    /**
     * 根据起点和终点经纬度获取时长（分钟）和距离（米）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(11);

        return ResponseResult.success(directionResponse);
    }


}
