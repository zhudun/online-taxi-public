package com.yz.apidriver.controller;

import com.yz.apidriver.service.PointService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-10:05
 * @Description: com.yz.apidriver.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest){

        return pointService.upload(apiDriverPointRequest);
    }
}
