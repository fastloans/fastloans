package com.app.lms.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {
    ADMIN(0),
    CLIENT(1);

    private final Integer type;
    UserType(Integer userType){
        this.type=userType;
    }

    @JsonValue
    public Integer getUserType(){
        return this.type;
    }
}
