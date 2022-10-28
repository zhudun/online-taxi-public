package com.yz.internalcommon.dto;

import lombok.Data;

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
    private Date driverBirthday;
    private String driverNation;
    private String driverContactAddress;
    private String licenseId;
    private Date getDriverLicenseDate;
    private Date driverLicenseOn;
    private Date driverLicenseOff;
    private Integer taxiDriver;
    private String networkCarIssueOrganization;
    private String certificateNo;
    private Date networkCarIssueDate;
    private Date getNetworkCarProofDate;
    private Date networkCarProofOn;
    private Date networkCarProofOff;
    private Date registerDate;
    private Integer commercialType;
    private String contractCompany;
    private Date contractOn;
    private Date contractOff;
    private Integer state;
    private Date gmtCreate;
    private Date gmtModified;
}
