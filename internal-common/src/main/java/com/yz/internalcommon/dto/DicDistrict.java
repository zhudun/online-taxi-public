package com.yz.internalcommon.dto;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/10/26-22:45
 * @Description: com.yz.internalcommon.dto
 * @version: 1.0
 */
@Data
public class DicDistrict {

    private String addressCode;

    private String addressName;

    private String parentAddressCode;

    private Integer level;
}
