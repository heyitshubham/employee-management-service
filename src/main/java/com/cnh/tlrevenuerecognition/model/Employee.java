package com.cnh.tlrevenuerecognition.model;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "employee", schema = "master")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 50)
    private String firstName ;
    @Column(length = 50)
    private String middleName ;
    @Column(length = 50)
    private String lastName ;
    @Column(length = 15, unique = true)
    private String mobileNumber ;
    @Column(length = 80, unique = true)
    private String email;
    @Column(length = 80)
    private String password;
    @Column(length = 10)
    private int tenantId ;
    private Boolean isActive;
    private Boolean firstLogin;
    @Column(length = 20)
    private String role;
    private Date createdAt;
    @Column(length = 50)
    private String createdBy;
    private Date modifiedAt;
    @Column(length = 50)
    private String modifiedBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return this.email != null ? this.email : this.mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
