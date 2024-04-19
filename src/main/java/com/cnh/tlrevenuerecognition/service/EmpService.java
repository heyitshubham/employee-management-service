package com.cnh.tlrevenuerecognition.service;

import com.cnh.tlrevenuerecognition.model.Employee;
import com.cnh.tlrevenuerecognition.payload.response.Response;
import com.cnh.tlrevenuerecognition.payload.users.EmpDto;
import com.cnh.tlrevenuerecognition.payload.users.EmpResp;

import java.util.List;

public interface EmpService {
    Response<List<EmpResp>> getAllEmployees();

    Response<EmpDto> addEmp(EmpDto empDto);

}
