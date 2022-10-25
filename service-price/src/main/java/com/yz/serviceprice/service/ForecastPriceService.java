package com.yz.serviceprice.service;

import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import com.yz.internalcommon.response.DirectionResponse;
import com.yz.internalcommon.response.ForecastPriceResponse;
import com.yz.serviceprice.mapper.PriceRuleMapper;
import com.yz.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-15:58
 * @Description: com.yz.apipassenger.service
 * @version: 1.0
 */
@Service
@Slf4j
public class ForecastPriceService {
    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

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

        ForeCastPriceDTO foreCastPriceDTO = new ForeCastPriceDTO();
        foreCastPriceDTO.setDepLatitude(depLatitude);
        foreCastPriceDTO.setDepLongitude(depLongitude);
        foreCastPriceDTO.setDestLatitude(destLatitude);
        foreCastPriceDTO.setDestLongitude(destLongitude);


        log.info("调用地图服务，查询距离和时长");
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(foreCastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离："+distance+"时长："+duration);

        log.info("调用计价规则");
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("city_code","110000");
        queryMap.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        PriceRule priceRule = priceRules.get(0);
        if(priceRules.size()==0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPITY.getCode(),CommonStatusEnum.PRICE_RULE_EMPITY.getMessage());
        }

        log.info("根据距离，时长，计价规则，计算价格");


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.35);

        return ResponseResult.success(direction);

    }


}
