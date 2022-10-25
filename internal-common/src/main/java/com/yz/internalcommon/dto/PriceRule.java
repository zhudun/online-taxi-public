package com.yz.internalcommon.dto;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-23:40
 * @Description: com.yz.internalcommon.dto
 * @version: 1.0
 */
@Data
public class PriceRule {

    private String cityCode;
    private String vehicleType;
    private double startFare;
    private int startMile;
    private double unitPricePerMile;
    private double unitPricePerMinute;



}
