package com.app.lms.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultException extends RuntimeException{
    private String message;
}
