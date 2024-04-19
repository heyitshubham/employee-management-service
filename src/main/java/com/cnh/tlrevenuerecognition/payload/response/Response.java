package com.cnh.tlrevenuerecognition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private Boolean success;
    private int resCode;
    private T data;
}
