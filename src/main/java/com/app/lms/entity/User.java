package com.app.lms.entity;

import com.app.lms.util.enums.SignupStage;
import com.app.lms.util.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"phone"})
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Lob
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone",length = 20)
    private String phone;

    @Column(name = "user_type",nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private UserType userType;

    @Column(name = "signup_stage",nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private SignupStage signupStage;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
