package com.yz.internalcommon.response;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-16:08
 * @Description: com.yz.internalcommon.response
 * @version: 1.0
 */
@Data
public class ForecastPriceResponse {
    private double price;
    private String cityCode;
    private String vehicleType;
}
