package com.scsb.t.service;

import com.scsb.t.entity.EmployeeEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String authority;

    @Getter
    private String empId;
    private List<GrantedAuthority> authorities;

    // 建構子
    public CustomUserDetails(EmployeeEntity employeeEntity) {
        this.username = employeeEntity.getName();
        this.empId = employeeEntity.getEmpId();
        this.password = employeeEntity.getPassword();
        this.authority = employeeEntity.getAuthority();
        this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(this.authority);
    }

    // 您可以在此添加更多的getters以取得其他欄位

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }



    @Override
    public String getUsername() {
        return username;
    }

    // 以下的方法，您可以根據您的需求來實現
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

