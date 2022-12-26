package com.yz.internalcommon.util;

/**
 * @Author: yangzhen
 * @Date 2022/12/26-19:11
 * @Description: com.yz.internalcommon.util
 * @version: 1.0
 */
public class SsePrefixUtils {

    public static  final String sperator = "$";

    public  static String generatorSseKey(Long userId , String identity){
        return userId+sperator+identity;
    }
}
