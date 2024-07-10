package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAmountDto {

    @JsonProperty("balance_amount")
    private BigDecimal balanceAmount;

    @JsonProperty("due_amount")
    private BigDecimal dueAmount;

}
