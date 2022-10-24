package com.yz.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: yangzhen
 * @Date 2022/10/23-0:10
 * @Description: com.yz.servicepassengeruser.dto
 * @version: 1.0
 */
@Data
public class PassengerUser {

    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String passengerPhone;
    private String passengerName;
    private byte passengerGender;
    private byte state;
    private String profilePhoto;
}
