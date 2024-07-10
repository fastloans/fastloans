package com.app.lms.dto;

import com.app.lms.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class InstalmentDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("due_date")
    private String dueDate;

    @JsonProperty("fine")
    private BigDecimal fine;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonCreator
    public InstalmentDto(@JsonProperty("id") Long id,
                   @JsonProperty("amount") BigDecimal amount,
                   @JsonProperty("due_slot") Integer dueSlot,
                   @JsonProperty("fine") BigDecimal fine,
                   @JsonProperty("is_paid") Boolean isPaid
    ){
        this.id=id;
        this.amount=amount;
        this.isPaid=isPaid;
        this.fine = fine;
        this.dueDate = TimeUtil.formatNSlot(dueSlot,"dd MMM YYYY");
        this.totalAmount = amount.add(fine);
    }
}
