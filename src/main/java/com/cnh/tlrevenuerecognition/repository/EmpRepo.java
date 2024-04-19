package com.cnh.tlrevenuerecognition.repository;
import com.cnh.tlrevenuerecognition.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpRepo extends JpaRepository<Employee,String>{
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByMobileNumber(String phone);
}
