package com.app.lms.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CreatedBy {
    SPRING(0),
    ADMIN(1);

    private final Integer intCreatedBy;
    CreatedBy(Integer createdBy){
        this.intCreatedBy=createdBy;
    }

    @JsonValue
    public Integer getCreatedBy(){
        return this.intCreatedBy;
    }
}
