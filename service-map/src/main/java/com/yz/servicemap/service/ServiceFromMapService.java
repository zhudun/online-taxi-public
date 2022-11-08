package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: yangzhen
 * @Date 2022/11/8-18:25
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceClient serviceClient;


    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name){

        return serviceClient.add(name);

    }
}
