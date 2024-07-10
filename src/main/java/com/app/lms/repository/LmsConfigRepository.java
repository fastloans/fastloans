package com.app.lms.repository;

import com.app.lms.entity.LmsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LmsConfigRepository extends JpaRepository<LmsConfig, Long> {
    @Query("SELECT c.value from LmsConfig c WHERE c.key = :key")
    String getKey(String key);
}