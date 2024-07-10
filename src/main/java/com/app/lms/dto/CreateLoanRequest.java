package com.app.lms.dto;

import com.app.lms.util.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {

    @JsonProperty("amount")
    @NotNull
    @Positive
    private BigDecimal amount;

    @JsonProperty("end_slot")
    @NotNull
    private Integer endSlot;

    @JsonProperty("loan_type")
    @NotNull
    private LoanType loanType;

    @JsonProperty("remark")
    @NotNull
    private String remark;

    @JsonProperty("interest")
    @NotNull
    @Positive
    private BigDecimal interest;

}
