package com.app.lms.exception.handler;

import com.app.lms.dto.ErrorResponse;
import com.app.lms.exception.DefaultException;
import com.app.lms.exception.InCompleteSignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse<Void> unknownException(Exception e){
        String errorMsg = (e.getCause() != null)?e.getCause().getMessage():e.getMessage();
        return ErrorResponse.<Void>builder()
                .errorMessage("Oops!! Something Went Wrong...")
                .developerMessage(errorMsg)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(DefaultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<Void> defaultException(DefaultException e){
        String errorMsg = e.getMessage();
        return ErrorResponse.<Void>builder()
                .errorMessage(errorMsg)
                .developerMessage(errorMsg)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(InCompleteSignUpException.class)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public ErrorResponse<Void> incompleteSignUpException(InCompleteSignUpException e){
        String errorMsg = e.getMessage();
        return ErrorResponse.<Void>builder()
                .errorMessage(errorMsg)
                .developerMessage(errorMsg)
                .statusCode(HttpStatus.MOVED_PERMANENTLY.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<Void> validationError(MethodArgumentNotValidException e){
        String errorMsg = e.getBindingResult().getFieldErrors().getFirst().getField() + " " +e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        return ErrorResponse.<Void>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(errorMsg)
                .developerMessage(errorMsg)
                .build();
    }
}
