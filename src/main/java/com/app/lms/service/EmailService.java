package com.app.lms.service;

import com.app.lms.dto.Email;
import com.app.lms.dto.EmailAttachment;
import com.app.lms.dto.EmailWithAttachment;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(Email email){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(email.getTo());
        mail.setSubject(email.getSubject());
        mail.setText(email.getBody());
        javaMailSender.send(mail);
    }

    public void sendEmail(EmailWithAttachment email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody());
        for(EmailAttachment attachment : email.getAttachments()){
            helper.addAttachment(attachment.getFileName(),attachment.getFile());
        }
        javaMailSender.send(message);
    }
}
