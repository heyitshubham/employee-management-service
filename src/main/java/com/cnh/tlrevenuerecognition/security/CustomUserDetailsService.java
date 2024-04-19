package com.cnh.tlrevenuerecognition.security;

import com.cnh.tlrevenuerecognition.exceptions.UserNotFoundException;
import com.cnh.tlrevenuerecognition.model.Employee;
import com.cnh.tlrevenuerecognition.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmpRepo empRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) { // Assuming email if username contains "@"
            Optional<Employee> empByEmail = empRepo.findByEmail(username);
            if (empByEmail.isPresent()) {
                return empByEmail.get();
            }
        } else {
            Optional<Employee> empByPhone = empRepo.findByMobileNumber(username);
            if (empByPhone.isPresent()) {
                return empByPhone.get();
            }
        }
        throw new UsernameNotFoundException("Employee not found with username: " + username);
    }
}
