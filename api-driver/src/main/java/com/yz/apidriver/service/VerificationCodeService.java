package com.yz.apidriver.service;

import com.yz.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/6-16:31
 * @Description: com.yz.apidriver.service
 * @version: 1.0
 */
@Service
public class VerificationCodeService {

    public ResponseResult checkAndSendVerificationCode(String driverPhone){
        //查询service-driver-user，该手机号的司机是否存在

        //获取验证码

        //调用第三方发送验证码

        //存入redis

        return ResponseResult.success("");
    }


}
