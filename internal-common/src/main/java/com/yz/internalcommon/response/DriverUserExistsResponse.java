package com.yz.internalcommon.response;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/11/6-17:55
 * @Description: com.yz.internalcommon.response
 * @version: 1.0
 */
@Data
public class DriverUserExistsResponse {

    private String driverPhone;

    private int ifExists;
}
