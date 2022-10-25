package com.yz.internalcommon.request;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/10/25-15:52
 * @Description: com.yz.internalcommon.dto
 * @version: 1.0
 */
@Data
public class ForeCastPriceDTO {

    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
}
