package com.app.lms.entity;

import com.app.lms.util.enums.LoanType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loan", indexes = {
        @Index(name = "idx_loan_user_id", columnList = "user_id"),
        @Index(name = "idx_loan_contract_id", columnList = "contract_id")
})
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "loan_type", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private LoanType loanType;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal amount;

    @Builder.Default
    @Column(name = "interest", precision = 9, scale = 2)
    private BigDecimal interest = BigDecimal.ZERO;

    @Column(name = "start_slot",nullable = false)
    private Integer startSlot;

    @Column(name = "end_slot")
    private Integer endSlot;

    @Column(name = "remark", length = 250)
    private String remark;

    @Column(name = "contract_id")
    private Long contractId;

    @Builder.Default
    @Column(name = "is_paid")
    @JdbcTypeCode(SqlTypes.TINYINT)
    private Boolean isPaid = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}