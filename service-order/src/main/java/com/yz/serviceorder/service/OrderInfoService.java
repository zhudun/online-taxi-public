package com.yz.serviceorder.service;

import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.OrderConstants;
import com.yz.internalcommon.dto.OrderInfo;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.serviceorder.mapper.OrderInfoMapper;
import com.yz.serviceorder.remote.ServicePriceClient;
import jodd.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.Request;

import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date 2022/11/12-15:52
 * @Description: com.yz.serviceorder.service
 * @version: 1.0
 */
@Service
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ServicePriceClient servicePriceClient;

    public ResponseResult add(OrderRequest orderRequest){
        //需要判断计价规则的版本是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if(!aNew.getData()){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }


        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);


        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success();
    }


}
