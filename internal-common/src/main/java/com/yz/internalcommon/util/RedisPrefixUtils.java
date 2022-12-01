package com.yz.internalcommon.util;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-23:21
 * @Description: com.yz.internalcommon.util
 * @version: 1.0
 */
public class RedisPrefixUtils {

    //乘客验证码的前缀
    public static String verificationCodePrefix = "verification-code-";

    //token的前缀
    public static String tokenPrefix = "token-";

    //黑名单设备号
    public static String blackDeviceCodePrefix = "black-device-";

    /**
     * 根据手机号生成验证码的key
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorKeyByPhone(String phone,String identity){
        return verificationCodePrefix +identity+"-"+ phone;
    }

    /**
     * 根据手机号和身份标识生成tokenkey
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String passengerPhone,String identity,String tokenType){
        return tokenPrefix+passengerPhone+"-"+identity+"-"+tokenType;
    }
}
