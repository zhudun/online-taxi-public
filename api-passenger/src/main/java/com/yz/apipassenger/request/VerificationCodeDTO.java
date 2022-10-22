package com.yz.apipassenger.request;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/10/21-22:42
 * @Description: com.yz.apipassenger.request
 * @version: 1.0
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
