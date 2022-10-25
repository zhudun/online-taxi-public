package com.yz.apipassenger.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-15:58
 * @Description: com.yz.apipassenger.service
 * @version: 1.0
 */
@Service
@Slf4j
public class ForecastPriceService {

    /**
     * 根据出发地和目的地经纬度  计算预估价
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude){

        log.info("出发地的经度:"+ depLongitude);
        log.info("出发地的纬度:"+ depLatitude);
        log.info("到达地的经度:"+ destLongitude);
        log.info("到达地的经度:"+ destLatitude);

        log.info("调用计价服务，计算价格");


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.35);

        return ResponseResult.success(forecastPriceResponse);

    }


}
