package com.app.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmailWithAttachment extends Email{
    private List<EmailAttachment> attachments;

    public EmailWithAttachment(String to, String subject, String body, List<EmailAttachment> attachments) {
        super(to,subject,body);
        this.attachments = attachments;
    }
}
