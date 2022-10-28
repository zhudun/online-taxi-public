package com.yz.internalcommon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Author: yangzhen
 * @Date 2022/10/28-15:42
 * @Description: com.yz.internalcommon.dto
 * @version: 1.0
 */
@Data
public class DriverUser {

    private Integer id;
    private String address;
    private String driverName;
    private String driverPhone;
    private Integer driverGender;
    private LocalDate driverBirthday;
    private String driverNation;
    private String driverContactAddress;
    private String licenseId;
    private LocalDate getDriverLicenseDate;
    private LocalDate driverLicenseOn;
    private LocalDate driverLicenseOff;
    private Integer taxiDriver;
    private String networkCarIssueOrganization;
    private String certificateNo;
    private LocalDate networkCarIssueDate;
    private LocalDate getNetworkCarProofDate;
    private LocalDate networkCarProofOn;
    private LocalDate networkCarProofOff;
    private LocalDate registerDate;
    private Integer commercialType;
    private String contractCompany;
    private LocalDate contractOn;
    private LocalDate contractOff;
    private Integer state;
    private Date gmtCreate;
    private Date gmtModified;
}
