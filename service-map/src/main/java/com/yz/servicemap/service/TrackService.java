package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TrackResponse;
import com.yz.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-5:33
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class TrackService {

    @Autowired
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){

        return trackClient.add(tid);
    }
}
