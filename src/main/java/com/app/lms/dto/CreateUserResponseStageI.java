package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponseStageI {
    @JsonProperty("token")
    private String token;

    @JsonProperty("redirect_to")
    private String redirectTo;
}
