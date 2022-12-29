package com.yz.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
        String fareType = cityCode + "$"+vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("city_code",cityCode);
        wrapper.eq("vehicle_type",vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 0;
        if(priceRules.size()>0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(),CommonStatusEnum.PRICE_RULE_EXISTS.getMessage());
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult edit(PriceRule priceRule){
        //拼接票价类型fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$"+vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("city_code",cityCode);
        wrapper.eq("vehicle_type",vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 0;
        if(priceRules.size()>0){

            PriceRule latestPriceRule = priceRules.get(0);
            Integer startMile = latestPriceRule.getStartMile();
            Double startFare = latestPriceRule.getStartFare();
            Double unitPricePerMile = latestPriceRule.getUnitPricePerMile();
            Double unitPricePerMinute = latestPriceRule.getUnitPricePerMinute();
            if(startFare.doubleValue()==priceRule.getStartFare().doubleValue()&&
            startMile.intValue()==priceRule.getStartMile().intValue()&&
            unitPricePerMile.doubleValue()==priceRule.getUnitPricePerMile().doubleValue()&&
            unitPricePerMinute.doubleValue()==priceRule.getUnitPricePerMinute().doubleValue()){
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EDIT.getCode(),CommonStatusEnum.PRICE_RULE_NOT_EDIT.getMessage());
            }
            fareVersion = latestPriceRule.getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult<PriceRule> getNewestVersion(String fareType){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fare_type",fareType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.size()>0){
            return ResponseResult.success(priceRules.get(0));
        }else{
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }

    }

    public ResponseResult<Boolean> isNew(String fareType,int fareVersion){
        ResponseResult<PriceRule> newestVersionPriceRule = getNewestVersion(fareType);
        if(newestVersionPriceRule.getCode() == CommonStatusEnum.PRICE_RULE_EMPTY.getCode()){
//            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
            return  ResponseResult.success(false);
        }
        PriceRule priceRule = newestVersionPriceRule.getData();
        Integer fareVersionDB = priceRule.getFareVersion();
        if(fareVersionDB > fareVersion){
            return ResponseResult.success(false);
        }else{
            return ResponseResult.success(true);

        }
    }


    public ResponseResult<Boolean> ifExists(PriceRule priceRule){
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();

        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        if (priceRules.size() > 0){
            return ResponseResult.success(true);
        }else {
            return ResponseResult.success(false);
        }
    }
}
