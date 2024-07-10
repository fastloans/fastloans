package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

    @JsonProperty("phone")
    @NotBlank
    @Length(min = 10,max = 12,message = "Invalid phone number")
    private String phone;

    @JsonProperty("password")
    @NotBlank
    @Length(min = 8,message = "Invalid password")
    private String password;

}