package com.app.lms.dto;

import com.app.lms.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(Long id,String name, String phone) implements Serializable {
}