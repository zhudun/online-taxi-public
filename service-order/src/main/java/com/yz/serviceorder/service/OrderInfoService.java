package com.yz.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.OrderConstants;
import com.yz.internalcommon.dto.OrderInfo;
import com.yz.internalcommon.dto.PriceRule;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.internalcommon.util.RedisPrefixUtils;
import com.yz.serviceorder.mapper.OrderInfoMapper;
import com.yz.serviceorder.remote.ServicePriceClient;
import jodd.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.Request;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public ResponseResult add(OrderRequest orderRequest){
        //需要判断计价规则的版本是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if(!aNew.getData()){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }


        //需要判断下单的设备是否是黑名单设备
        String deviceCode  = orderRequest.getDeviceCode();
        //生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix+deviceCode;

        //设置key，看原来有没有key
        if (isBlackDevice(deviceCodeKey))
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getMessage());

        //判断有正在进行的订单不允许下单
        if(isOrderGoingOn(orderRequest.getPassengerId())>0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getMessage());
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

    private boolean isBlackDevice(String deviceCodeKey) {
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if(aBoolean){
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if(i>=2){
                return true;
            }else{
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        }else{
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey,"1",1L,TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 是否有业务中的订单
     * @param passengerId
     * @return
     */
    private int isOrderGoingOn(Long passengerId){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id",passengerId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstants.ORDER_START)
                .or().eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status",OrderConstants.TO_START_PAY)
        );
        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        return validOrderNumber;
    }


}
