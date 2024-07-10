package com.app.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination<T> {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("has_next")
    private boolean hasNext;

    @JsonProperty("data")
    private List<T> data;

    public Pagination(boolean hasNext,List<T> data){
        this.success = true;
        this.hasNext = hasNext;
        this.data = data;
    }
}
