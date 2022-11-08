package com.yz.internalcommon.response;

import lombok.Data;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-4:52
 * @Description: com.yz.internalcommon.response
 * @version: 1.0
 */
@Data
public class TerminalResponse {

    private String tid;

    private Long carId;

    private String longitude ;
    private String latitude ;
}
