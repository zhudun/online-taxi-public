package com.yz.internalcommon.request;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/12/15-23:56
 * @Description: com.yz.internalcommon.request
 * @version: 1.0
 */
@Data
public class PriceRuleIsNewRequest {

    private String fareType;

    private Integer fareVersion;
}

