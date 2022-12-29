package com.yz.serviceprice.controller;

import com.yz.serviceprice.service.PriceService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-16:47
 * @Description: com.yz.serviceprice.controller
 * @version: 1.0
 */
@RestController
public class ForecastPriceController {

    @Autowired
    private PriceService forecastPriceService;

    /**
     * 计算预估价格
     * @param foreCastPriceDTO
     * @return
     */
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

    /**
     * 计算实际价格
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    @PostMapping("/calculate-price")
    public ResponseResult<Double>  calculatePrice(@RequestParam Integer distance , @RequestParam Integer duration, @RequestParam String cityCode, @RequestParam String vehicleType){
        return forecastPriceService.calculatePrice(distance,duration,cityCode,vehicleType);
    }
}
