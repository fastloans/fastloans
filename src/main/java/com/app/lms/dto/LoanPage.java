package com.app.lms.dto;

import com.app.lms.util.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPage {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("interest")
    private BigDecimal interest;

    @JsonProperty("percent_paid")
    private BigDecimal percentPaid;

    @JsonProperty("loan_type")
    private LoanType loanType;

}
