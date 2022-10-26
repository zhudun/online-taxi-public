package com.yz.servicemap.service;

import com.yz.internalcommon.constant.AmapConfigConstants;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-23:07
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDicDistrict(String keywords){

        //请求地图
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrict);

        //解析结果

        //插入数据库



        return ResponseResult.success();
    }
}
