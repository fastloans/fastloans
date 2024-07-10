package com.app.lms.framework;

import com.app.lms.dto.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class Session {
    public static AuthenticatedUser getAuthenticatedUser(){
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }
}
