package com.app.lms.service;

import com.app.lms.dto.AuthenticatedUser;
import com.app.lms.dto.Email;
import com.app.lms.dto.EmailAttachment;
import com.app.lms.dto.EmailWithAttachment;
import com.app.lms.entity.User;
import com.app.lms.framework.Excel;
import com.app.lms.store.UserStore;
import com.app.lms.util.Bcrypt;
import com.app.lms.util.JwtUtils;
import com.app.lms.util.TimeUtil;
import com.app.lms.util.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CmdRunner implements CommandLineRunner {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    Bcrypt bcrypt;

    @Autowired
    UserStore userStore;

    @Autowired
    EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println(bcrypt.generateHash("Singh@2020"));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
