package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.PointRequest;
import com.yz.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-6:22
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class PointService {

    @Autowired
    PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }
}

