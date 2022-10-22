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
    SUCCESS(1,"success"),
    FAIL(0,"fail")


    ;

    private int code;
    private String message;

    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
