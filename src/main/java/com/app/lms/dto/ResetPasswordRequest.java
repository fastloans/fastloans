package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @JsonProperty("password")
    @NotBlank
    @Length(min = 8,message = "Invalid password")
    private String password;
}
