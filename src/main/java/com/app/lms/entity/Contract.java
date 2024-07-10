package com.app.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contract", indexes = {
        @Index(name = "idx_contract_loan_id", columnList = "loan_id"),
        @Index(name = "idx_contract_file_name", columnList = "file_name")
})
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "file_name",length = 500)
    private String fileName;

    @Lob
    @Column(name = "image_content",columnDefinition = "LONGBLOB")
    private byte[] imageContent;
}
