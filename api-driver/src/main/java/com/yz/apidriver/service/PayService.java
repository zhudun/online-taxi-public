package com.yz.apidriver.service;

import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/12/29-23:09
 * @Description: com.yz.apidriver.service
 * @version: 1.0
 */
@Service
public class PayService {

    public ResponseResult pushPayInfo(String orderId, String price, Long passengerId){
        // 封装消息

        // 推送消息

        return ResponseResult.success();
    }
}
