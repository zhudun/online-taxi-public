package com.yz.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangzhen
 * @Date 2022/11/29-16:33
 * @Description: com.yz.serviceprice.service
 * @version: 1.0
 */
@Service
public class PriceRuleService {

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult add(PriceRule priceRule){

        //拼接票价类型fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("city_code",cityCode);
        wrapper.eq("vehicle_type",vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 0;
        if(priceRules.size()>0){
            fareVersion = priceRules.get(0).getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

}
