package com.cnh.tlrevenuerecognition.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    String resourceName;
    Object username;


    public UserNotFoundException(String resourceName,Object username) {
        super(String.format("%s not found with %s", resourceName,username ));
        this.resourceName = resourceName;
        this.username = username;
    }
}
