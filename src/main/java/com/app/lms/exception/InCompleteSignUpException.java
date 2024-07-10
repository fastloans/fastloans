package com.app.lms.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InCompleteSignUpException extends DefaultException{
    public InCompleteSignUpException(String s) {
        super(s);
    }
}
