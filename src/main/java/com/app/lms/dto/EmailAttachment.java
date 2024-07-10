package com.app.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAttachment {
    private String fileName;
    private ByteArrayResource file;
}
