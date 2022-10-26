package com.yz.serviceprice.service;

import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.ForeCastPriceDTO;
import com.yz.internalcommon.response.DirectionResponse;
import com.yz.internalcommon.response.ForecastPriceResponse;
import com.yz.internalcommon.util.BigDecimalUtils;
import com.yz.serviceprice.mapper.PriceRuleMapper;
import com.yz.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        double price = getPrice(distance, duration, priceRule);


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        log.info("price"+price);

        return ResponseResult.success(forecastPriceResponse);

    }


    /**
     * 根据距离和时长和计价规则 计算最终价格
     * @param distance  距离
     * @param duration  时长
     * @param priceRule  计价规则
     * @return
     */
    private static double getPrice(Integer distance,Integer duration,PriceRule priceRule){
        double price = 0.0;

        //起步价 元
        Double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);

        //总里程 km
        double distanceMile = BigDecimalUtils.divide(distance,1000);
        //起步里程 km
        double startMile = (double) priceRule.getStartMile();
        //起步之外的里程 km
        double distanceSubtract = BigDecimalUtils.subtract(distanceMile,startMile);
        //起步之外收费的里程数 km
        double mile = distanceSubtract<0?0:distanceSubtract;
        //计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();
        //起步之外的里程价格
        double mileFare = BigDecimalUtils.multiply(mile,unitPricePerMile);//setScale设置精度
        //加上起步之外里程的价格后的总价格
        price = BigDecimalUtils.add(price,mileFare);

        //时长的分钟数
        double durationMinute = BigDecimalUtils.divide(duration,60);
        //计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        //时长费用
        double timeFare = BigDecimalUtils.multiply(durationMinute,unitPricePerMinute);
        price = BigDecimalUtils.add(price,timeFare);

        BigDecimal priceBigDecimal = BigDecimal.valueOf(price);
        priceBigDecimal = priceBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);

        return priceBigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);

        System.out.println(getPrice(6500,1800,priceRule));
    }

}
