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
    private Double startFare;
    private Integer startMile;
    private Double unitPricePerMile;
    private Double unitPricePerMinute;



}
