package com.app.lms.dto;

import com.app.lms.util.enums.CreatedBy;
import com.app.lms.util.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedUser implements Authentication {
    private Long userId;
    private String name;
    private String phone;
    private UserType userType;

    @Builder.Default
    private CreatedBy createdBy = CreatedBy.SPRING;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.createdBy == CreatedBy.ADMIN){
            return List.of(new SimpleGrantedAuthority(UserType.ADMIN.name()));
        }
        return List.of(new SimpleGrantedAuthority(this.userType.name()));
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getDetails() {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getPrincipal() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    @JsonIgnore
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
}
