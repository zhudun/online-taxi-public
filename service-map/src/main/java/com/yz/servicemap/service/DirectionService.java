package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.DirectionResponse;
import com.yz.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-17:27
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 根据起点和终点经纬度获取时长（分钟）和距离（米）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        //调用第三方地图接口
        DirectionResponse directionResponse = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);


        return ResponseResult.success(directionResponse);
    }


}
