package com.yz.servicemap.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-23:06
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDicDistrict(String keywords){


        return dicDistrictService.initDicDistrict(keywords);
    }
}
