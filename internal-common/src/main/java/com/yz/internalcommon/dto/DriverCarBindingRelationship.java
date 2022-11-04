package com.yz.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yz
 * @since 2022-11-04
 */
@Data
public class DriverCarBindingRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 司机id
     */
    private Long driverId;

    /**
     * 车辆id
     */
    private Long carId;

    /**
     * 状态：1：绑定  2：解绑
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;


}
