package com.app.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanAmountMapping {

    private Long loanId;

    private BigDecimal amount;

    public LoanAmountMapping(Long loanId,BigDecimal amount,BigDecimal fine){
        this.loanId=loanId;
        this.amount = amount.add(fine);
    }
}
