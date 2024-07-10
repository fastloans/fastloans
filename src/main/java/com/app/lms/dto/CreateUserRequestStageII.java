package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestStageII {

    @JsonProperty("address")
    private String address;

    @JsonProperty("pan_no")
    private String panNo;

    @JsonProperty("aadhaar_no")
    private String aadhaarNo;

}
