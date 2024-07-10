package com.app.lms.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("pan_no")
    private String panNo;

    @JsonProperty("aadhaar_no")
    private String aadhaarNo;

    @JsonProperty("address")
    private String address;

    @JsonProperty("add_loan_btn")
    @Builder.Default
    private Boolean addLoanBtn = false;
}
