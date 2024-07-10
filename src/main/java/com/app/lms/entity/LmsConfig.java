package com.app.lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "lms_config", uniqueConstraints = {
        @UniqueConstraint(name = "uc_lmsconfig_key", columnNames = {"`key`"})
})
public class LmsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`key`",nullable = false)
    private String key;

    @Lob
    @Column(name = "value")
    private String value;
}
