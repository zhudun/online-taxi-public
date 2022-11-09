package com.yz.internalcommon.request;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-10:07
 * @Description: com.yz.internalcommon.request
 * @version: 1.0
 */
@Data
public class ApiDriverPointRequest {

    public Long carId;

    private PointDTO[] points;
}
