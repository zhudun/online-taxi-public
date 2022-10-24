package com.yz.apipassenger.service;

import com.yz.apipassenger.remote.ServicePassengerUserClient;
import com.yz.internalcommon.dto.PassengerUser;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.dto.TokenResult;
import com.yz.internalcommon.request.VerificationCodeDTO;
import com.yz.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/10/24-21:22
 * @Description: com.yz.apipassenger.service
 * @version: 1.0
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken:" + accessToken);
        //解析accessToken 拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("手机号:" + phone);

        //根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);


        return ResponseResult.success(userByPhone.getData());

    }
}
