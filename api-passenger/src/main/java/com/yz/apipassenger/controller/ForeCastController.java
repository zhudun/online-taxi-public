package com.yz.apipassenger.controller;

import com.yz.apipassenger.service.ForecastPriceService;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import com.yz.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-12:13
 * @Description: com.yz.apipassenger.controller
 * @version: 1.0
 */
@RestController
@Slf4j
public class ForeCastController {
    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForeCastPriceDTO foreCastPriceDTO){
        String depLongitude = foreCastPriceDTO.getDepLongitude();
        String depLatitude = foreCastPriceDTO.getDepLatitude();
        String destLongitude = foreCastPriceDTO.getDestLongitude();
        String destLatitude = foreCastPriceDTO.getDestLatitude();


        log.info("出发地的经度:"+ depLongitude);
        log.info("出发地的纬度:"+ depLatitude);
        log.info("到达地的经度:"+ destLongitude);
        log.info("到达地的经度:"+ destLatitude);



        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
