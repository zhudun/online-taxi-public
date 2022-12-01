package com.yz.serviceprice.controller;

import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yangzhen
 * @Date 2022/11/29-16:34
 * @Description: com.yz.serviceprice.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;


    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule){
        return priceRuleService.add(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule){
        return priceRuleService.edit(priceRule);
    }

    /**
     * 查询最新的计价规则
     * @param fareType
     * @return
     */
    @GetMapping("/get-newest-version")
    public ResponseResult<PriceRule> getNewestVersion(@RequestParam String fareType){
        return priceRuleService.getNewestVersion(fareType);
    }

    @GetMapping("/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType,@RequestParam Integer fareVersion){
        return priceRuleService.isNew(fareType,fareVersion);
    }

}
