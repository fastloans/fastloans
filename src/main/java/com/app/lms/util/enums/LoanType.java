package com.app.lms.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoanType {
    DAILY(0),
    MONTHLY(1),
    WEEKLY(2),
    INFORMAL(3);

    private final Integer intLoanType;
    LoanType(Integer loanType){
        this.intLoanType=loanType;
    }

    @JsonValue
    public Integer getLoanType(){
        return this.intLoanType;
    }
}
