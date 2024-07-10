package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse <T> {

    @JsonProperty("success")
    @Builder.Default
    private Boolean success = Boolean.FALSE;

    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("error_msg")
    private String errorMessage;

    @JsonProperty("dev_msg")
    private String developerMessage;

    @JsonProperty("data")
    private T data;

}
