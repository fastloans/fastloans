package com.app.lms.dto;

import com.app.lms.util.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestStageI {

    @JsonProperty("name")
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty("phone")
    @NotBlank
    @Length(min = 10,max = 12,message = "Invalid phone number")
    private String phone;

    @JsonProperty("password")
    @NotBlank
    @Length(min = 8,message = "Password must be greater than or equal to 8 characters.")
    private String password;

    @JsonProperty("user_type")
    @NotNull
    private UserType userType;
}
