package com.cnh.tlrevenuerecognition.controller;

import com.cnh.tlrevenuerecognition.payload.response.JwtRequest;
import com.cnh.tlrevenuerecognition.payload.response.JwtResponse;
import com.cnh.tlrevenuerecognition.payload.response.Response;
import com.cnh.tlrevenuerecognition.security.JWTTokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JWTTokenHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<Response<JwtResponse>> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.helper.generateToken(userDetails);

        JwtResponse res = JwtResponse.builder()
                .token(token)
                .role(userDetails.getAuthorities().toString())
                .username(userDetails.getUsername()).build();
        Response<JwtResponse> jwtRes = new Response<>();
        jwtRes.setResCode(200);
        jwtRes.setSuccess(true);
        jwtRes.setData(res);
        return new ResponseEntity<>(jwtRes, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<String>> exceptionHandler() {
        Response<String> errRes = new Response<>(false, 401, "Invalid Credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errRes);
    }
}
