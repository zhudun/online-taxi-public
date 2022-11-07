package com.yz.apidriver.service;

import com.yz.apidriver.remote.ServiceDriverUserClient;
import com.yz.apidriver.remote.ServiceVerificationCodeClient;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.DriverCarConstants;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.DriverUserExistsResponse;
import com.yz.internalcommon.response.NumberCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/6-16:31
 * @Description: com.yz.apidriver.service
 * @version: 1.0
 */
@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    public ResponseResult checkAndSendVerificationCode(String driverPhone){
        //查询service-driver-user，该手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
        }
        log.info(driverPhone+"的司机存在");

        //获取验证码
        ResponseResult<NumberCodeResponse> verificationCodeResult = serviceVerificationCodeClient.getVerificationCode(6);
        NumberCodeResponse numberCodeResponse = verificationCodeResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        log.info("验证码:"+numberCode);

        //调用第三方发送验证码

        //存入redis

        return ResponseResult.success("");
    }


}
