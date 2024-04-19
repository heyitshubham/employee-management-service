package com.cnh.tlrevenuerecognition.service.impl;

import com.cnh.tlrevenuerecognition.model.Employee;
import com.cnh.tlrevenuerecognition.payload.response.Response;
import com.cnh.tlrevenuerecognition.payload.users.EmpDto;
import com.cnh.tlrevenuerecognition.payload.users.EmpResp;
import com.cnh.tlrevenuerecognition.repository.EmpRepo;
import com.cnh.tlrevenuerecognition.service.EmpService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    private final EmpRepo empRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmpServiceImpl(EmpRepo empRepo, ModelMapper modelMapper) {
        this.empRepo = empRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response<List<EmpResp>> getAllEmployees() {
        List<Employee> employees = empRepo.findAll();
        List<EmpResp> empRespList = new ArrayList<>();

        for (Employee employee : employees) {
            EmpResp empResp = modelMapper.map(employee, EmpResp.class);
            empRespList.add(empResp);
        }

        Response<List<EmpResp>> responseDTO = new Response<>(true, 200, empRespList);
        return responseDTO;
    }
    @Override
    public Response<EmpDto> addEmp(EmpDto empDto) {
        // Map EmpDto to Employee entity
//        Employee newEmployee = modelMapper.map(empDto, Employee.class);
        Employee newEmployee = mapDtoToEmployee(empDto);
        newEmployee.setCreatedAt(new Date());
        newEmployee.setCreatedBy("InitDevData");
        newEmployee.setFirstLogin(true);
        // Save the new employee
        Employee addedEmployee = empRepo.save(newEmployee);

        // Create response object
        Response<EmpDto> response = new Response<>(true,201,empDto);

        return response;
    }

    private Employee mapDtoToEmployee(EmpDto empDto) {
        Employee employee = new Employee();
        employee.setFirstName(empDto.getFirstName());
        employee.setMiddleName(empDto.getMiddleName());
        employee.setLastName(empDto.getLastName());
        employee.setMobileNumber(empDto.getMobileNumber());
        employee.setEmail(empDto.getEmail());
        employee.setTenantId(empDto.getTenantId());
        employee.setRole(empDto.getRole());
        employee.setPassword(this.passwordEncoder.encode(empDto.getPassword()));
        return employee;
    }

}
