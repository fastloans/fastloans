package com.app.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Builder.Default
    @Column(name = "address",length = 500)
    private String address = "";

    @Column(name = "pan_no",length = 10)
    private String panNo;

    @Column(name = "aadhaar_no",length = 12)
    private String aadhaarNo;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
