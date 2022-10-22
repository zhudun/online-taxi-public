package com.yz.apipassenger.service;

import com.yz.apipassenger.remote.ServiceVerificationcodeClient;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
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
    //乘客验证码的前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generatorCode(String passengerPhone){
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //key,value,过期时间
        String key = verificationCodePrefix+passengerPhone;
        //存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        //通过短信服务商，将短信验证码发送到手机上，阿里的短信服务，腾讯的短信通，华信的容联

        //返回值
        return ResponseResult.success("");
    }
}
