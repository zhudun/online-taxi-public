package com.yz.apipassenger.service;

import com.yz.apipassenger.remote.ServicePassengerUserClient;
import com.yz.apipassenger.remote.ServiceVerificationcodeClient;
import com.yz.internalcommon.constant.CommonStatusEnum;
import com.yz.internalcommon.constant.IdentityConstants;
import com.yz.internalcommon.constant.TokenConstants;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.request.VerificationCodeDTO;
import com.yz.internalcommon.response.NumberCodeResponse;
import com.yz.internalcommon.response.TokenResponse;
import com.yz.internalcommon.util.JwtUtils;
import com.yz.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yangzhen
 * @Date 2022/10/21-23:05
 * @Description: com.yz.apipassenger.service
 * @version: 1.0
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     *
     * @param passengerPhone 手机号
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> responseResult = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = responseResult.getData().getNumberCode();

        //key,value,过期时间
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone) ;
        //存入redis
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        //通过短信服务商，将短信验证码发送到手机上，阿里的短信服务，腾讯的短信通，华信的容联

        //返回值
        return ResponseResult.success("");
    }



    /**
     * 校验验证码
     *
     * @param passengerPhone   手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取验证码");

        //生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone) ;
        //根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value = " + codeRedis);

        //校验验证码
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }
        if(!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());

        }

        //判断原来是否有用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        //颁发令牌 不应该用魔法值，应该用常量
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        //将token存到redis中
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);



        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }

}
