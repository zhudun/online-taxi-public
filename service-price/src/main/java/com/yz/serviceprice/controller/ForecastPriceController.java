package com.yz.serviceprice.controller;

import com.yz.serviceprice.service.ForecastPriceService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-16:47
 * @Description: com.yz.serviceprice.controller
 * @version: 1.0
 */
@RestController
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForeCastPriceDTO foreCastPriceDTO){

        String depLongitude = foreCastPriceDTO.getDepLongitude();
        String depLatitude = foreCastPriceDTO.getDepLatitude();
        String destLongitude = foreCastPriceDTO.getDestLongitude();
        String destLatitude = foreCastPriceDTO.getDestLatitude();
        String cityCode = foreCastPriceDTO.getCityCode();
        String vehicleType = foreCastPriceDTO.getVehicleType();

        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude,cityCode,vehicleType);
    }
}
