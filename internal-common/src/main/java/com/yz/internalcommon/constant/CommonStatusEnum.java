package com.yz.internalcommon.constant;

import lombok.Getter;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-16:18
 * @Description: com.yz.internalcommon.constant
 * @version: 1.0
 */
@Getter
public enum CommonStatusEnum {
    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),

    /**
     * TOKEN类提示：1100-1199
     */
    TOKEN_ERROR(1199,"token错误"),


    /**
     * 成功
     */
    SUCCESS(1,"success"),
    /**
     * 失败
     */
    FAIL(0,"fail")


    ;

    private int code;
    private String message;

    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
