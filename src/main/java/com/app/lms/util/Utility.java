package com.app.lms.util;

import com.app.lms.util.enums.UserType;

public class Utility {
    public static String redirectFromLoginPage(UserType userType){
        return switch (userType){
            case ADMIN -> "Home";
            default -> "User";
        };
    }

    public static String redirectFromSignupPage(UserType userType){
        return switch (userType){
            case ADMIN -> "User";
            default -> "SignupStage1";
        };
    }
}
