package com.app.lms.dto;

import com.app.lms.util.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("redirect_to")
    private String redirectTo;

    @JsonProperty("user_type")
    private UserType userType;
}
