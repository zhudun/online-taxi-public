package com.yz.apipassenger.service;

import com.yz.apipassenger.remote.ServiceVerificationcodeClient;
import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String generatorCode(String passengerPhone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(7);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("remote number code"+numberCode);



        //存入redis
        System.out.println("存入redis");

        //返回值
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}
