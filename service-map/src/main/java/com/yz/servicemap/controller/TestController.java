package com.yz.servicemap.controller;

import com.yz.internalcommon.dto.DicDistrict;
import com.yz.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-17:20
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "service map...";
    }

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test-map")
    public String testMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("address_code","110000");
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
        System.out.println(dicDistricts);

        return "test-map";


    }
}
