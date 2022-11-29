package com.yz.serviceorder.service;

import com.yz.internalcommon.constant.OrderConstants;
import com.yz.internalcommon.dto.OrderInfo;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.OrderRequest;
import com.yz.serviceorder.mapper.OrderInfoMapper;
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

    public ResponseResult add(OrderRequest orderRequest){
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
