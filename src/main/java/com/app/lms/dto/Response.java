package com.app.lms.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response <T>{

    @JsonProperty("success")
    private static final Boolean success = Boolean.TRUE;

    @JsonProperty("data")
    private T data;

}
