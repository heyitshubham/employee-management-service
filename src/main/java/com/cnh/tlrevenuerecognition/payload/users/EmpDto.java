package com.cnh.tlrevenuerecognition.payload.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpDto {
    private String firstName ;
    private String middleName ;
    private String lastName ;
    private String mobileNumber ;
    private String email;
    private String password;
    private String role;
    private int tenantId ;
}
