package com.cnh.tlrevenuerecognition.controller;

import com.cnh.tlrevenuerecognition.model.Employee;
import com.cnh.tlrevenuerecognition.payload.response.Response;
import com.cnh.tlrevenuerecognition.payload.users.EmpDto;
import com.cnh.tlrevenuerecognition.payload.users.EmpResp;
import com.cnh.tlrevenuerecognition.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllEmployees() {
        Response responseDTO = empService.getAllEmployees();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Response<EmpDto>> addEmp(@RequestBody EmpDto empDto) {
        Response<EmpDto> response = empService.addEmp(empDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Response<EmpResp>> getEmpByPr(Principal principal){
        EmpResp empResp = new EmpResp();
        empResp.setFirstName(principal.getName());
        Response<EmpResp> response= new Response<EmpResp>(true,200,empResp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
