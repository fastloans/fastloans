package com.app.lms.repository;

import com.app.lms.dto.UserDto;
import com.app.lms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    @Query("SELECT new com.app.lms.dto.UserDto(u.id,u.name,u.phone) from User u WHERE u.userType!=0")
    Page<UserDto> getPaginatedUsers(Pageable pageable);

    @Query("SELECT new com.app.lms.dto.UserDto(u.id,u.name,u.phone) from User u where u.userType!=0 and (LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR u.phone LIKE CONCAT('%', :search, '%'))")
    Page<UserDto> getPaginatedUsers(String search,Pageable pageable);
}