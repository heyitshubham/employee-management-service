package com.cnh.tlrevenuerecognition.payload.users;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EmpResp {
    private UUID id;
    private String firstName ;
    private String middleName ;
    private String lastName ;
    private String mobileNumber ;
    private String email;
    private int tenantId ;
    private Boolean isActive;
    private Boolean firstLogin;
    private Date createdAt;
    private String createdBy;
    private Date modifiedAt;
    private Date modifiedBy;

}
