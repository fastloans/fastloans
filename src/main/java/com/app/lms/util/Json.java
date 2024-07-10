package com.app.lms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Json {

    private Json() {
        throw new IllegalStateException("Utility class");
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String dumps(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T loads(String json,Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json,clazz);
    }

    public static <T> T loads(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return objectMapper.readValue(json,typeReference);
    }

    public static <T,S> S convertTo(T obj,TypeReference<S> typeReference) {
        return objectMapper.convertValue(obj,typeReference);
    }
    public static <T,S> S convertTo(T obj,Class<S> clazz){
        return objectMapper.convertValue(obj,clazz);
    }
}
