package com.app.lms.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SignupStage {
    STAGEI(0),
    STAGEII(1);

    private Integer signupStage;

    SignupStage(Integer signupStage){
        this.signupStage=signupStage;
    }

    @JsonValue
    public Integer getSignupStage(){
        return this.signupStage;
    }
}
