package com.yz.servicemap.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.PointRequest;
import com.yz.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-6:23
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){

        return pointService.upload(pointRequest);
    }
}
