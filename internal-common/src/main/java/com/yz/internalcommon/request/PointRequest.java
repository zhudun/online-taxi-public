package com.yz.internalcommon.request;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-6:19
 * @Description: com.yz.internalcommon.request
 * @version: 1.0
 */

@Data
public class PointRequest {

    private String tid;

    private String trid;

    private PointDTO[] points;

}
