package com.yz.servicemap.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import com.yz.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-17:23
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/direction")
public class DirectionController
{
    @Autowired
    private DirectionService directionService;
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForeCastPriceDTO foreCastPriceDTO){

        String depLongitude = foreCastPriceDTO.getDepLongitude();
        String depLatitude = foreCastPriceDTO.getDepLatitude();
        String destLongitude = foreCastPriceDTO.getDestLongitude();
        String destLatitude = foreCastPriceDTO.getDestLatitude();

        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }




}
