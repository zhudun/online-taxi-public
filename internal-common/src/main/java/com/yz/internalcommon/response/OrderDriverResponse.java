package com.yz.internalcommon.response;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/12/9-20:47
 * @Description: com.yz.internalcommon.response
 * @version: 1.0
 */
@Data
public class OrderDriverResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;
}
