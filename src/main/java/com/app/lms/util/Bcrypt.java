package com.app.lms.util;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bcrypt {

    private final PasswordEncoder encoder;
    public String generateHash(String password){
        return encoder.encode(password);
    }

    public boolean matches(String password,String hash){
        return encoder.matches(password,hash);
    }
}
