package com.app.lms.dto;

import com.app.lms.util.TimeUtil;
import com.app.lms.util.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LoanDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("interest")
    private BigDecimal interest;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("loan_type")
    private LoanType loanType;

    @JsonCreator
    public LoanDto(@JsonProperty("id") Long id,
                   @JsonProperty("amount") BigDecimal amount,
                   @JsonProperty("interest") BigDecimal interest,
                   @JsonProperty("start_slot") Integer startSlot,
                   @JsonProperty("end_slot") Integer endSlot,
                   @JsonProperty("is_paid") Boolean isPaid,
                   @JsonProperty("remark") String remark,
                   @JsonProperty("loan_type") LoanType loanType
                   ){
        this.id=id;
        this.amount=amount;
        this.interest=interest;
        this.isPaid=isPaid;
        this.remark=remark;
        String format = "dd MMM YYYY";
        this.startDate = TimeUtil.formatNSlot(startSlot,format);
        this.endDate = TimeUtil.formatNSlot(endSlot,format);
        this.loanType=loanType;
    }
}
