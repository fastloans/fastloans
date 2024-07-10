package com.app.lms.service;

import com.app.lms.dto.*;
import com.app.lms.entity.User;
import com.app.lms.entity.UserInfo;
import com.app.lms.exception.DefaultException;
import com.app.lms.exception.InCompleteSignUpException;
import com.app.lms.store.UserInfoStore;
import com.app.lms.store.UserStore;
import com.app.lms.util.Bcrypt;
import com.app.lms.util.JwtUtils;
import com.app.lms.util.Utility;
import com.app.lms.util.enums.CreatedBy;
import com.app.lms.util.enums.SignupStage;
import com.app.lms.util.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    private final UserInfoStore userInfoStore;

    private final JwtUtils jwtUtils;

    private final Bcrypt bcrypt;

    public LoginResponse loginUser(LoginRequest body){
        String phone = body.getPhone();
        Optional<User> user = userStore.findUserByPhone(phone);
        if(user.isEmpty()){
            throw new DefaultException("Invalid phone number");
        }
        User existingUser = user.get();
        if(!bcrypt.matches(body.getPassword(), existingUser.getPassword())){
            throw new DefaultException("Invalid password");
        }
        if(existingUser.getSignupStage()!=SignupStage.STAGEII){
            throw new DefaultException("Signup not completed please ask admin to create Account.");
        }
        String token = getJwtTokenForUser(existingUser,CreatedBy.SPRING);
        String redirectTo = Utility.redirectFromLoginPage(existingUser.getUserType());
        return new LoginResponse(token,redirectTo,existingUser.getUserType());
    }

    public void forgotPassword(Long userId,ResetPasswordRequest body){
        Optional<User> user = userStore.findById(userId);
        if(user.isEmpty()){
            throw new DefaultException("User not found");
        }
        User existingUser = user.get();
        existingUser.setPassword(bcrypt.generateHash(body.getPassword()));
        userStore.save(existingUser);
    }

    public CreateUserResponseStageI createNewUser(CreateUserRequestStageI body){
        String phone = body.getPhone();
        Optional<User> existingUser = userStore.findUserByPhone(phone);
        if(existingUser.isPresent()){
            throw new DefaultException("User already exists with given phone");
        }
        User user = User.builder()
                .name(body.getName())
                .phone(body.getPhone())
                .userType(body.getUserType())
                .password(bcrypt.generateHash(body.getPassword()))
                .signupStage(body.getUserType()==UserType.CLIENT?SignupStage.STAGEI:SignupStage.STAGEII)
                .build();
        user = userStore.save(user);
        String token = getJwtTokenForUser(user,CreatedBy.ADMIN);
        String redirectTo = Utility.redirectFromSignupPage(body.getUserType());
        return new CreateUserResponseStageI(token,redirectTo);
    }

    public void createNewUser(CreateUserRequestStageII body,Long userId){
        Optional<User> optionalUser = userStore.findById(userId);
        if(optionalUser.isEmpty()){
            throw new DefaultException("No user found");
        }
        User user = optionalUser.get();
        if(user.getSignupStage()==SignupStage.STAGEII){
            throw new DefaultException("User already exists with given phone");
        }
        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .panNo(body.getPanNo())
                .aadhaarNo(body.getAadhaarNo())
                .address(body.getAddress())
                .build();
        userInfoStore.save(userInfo);
        user.setSignupStage(SignupStage.STAGEII);
        userStore.save(user);
    }

    public String getUserToken(Long userId){
        Optional<User> user = userStore.findById(userId);
        if(user.isEmpty()){
            throw new DefaultException("No User Found.");
        }
        User existingUser = user.get();
        return getJwtTokenForUser(existingUser,CreatedBy.ADMIN);
    }

    public String getJwtTokenForUser(User user,CreatedBy createdBy){
        AuthenticatedUser authUser = AuthenticatedUser.builder()
                .userId(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .createdBy(createdBy)
                .build();
        return jwtUtils.generateToken(authUser);
    }

    public Pagination<UserDto> getPaginatedUsers(Integer pageNo, String search){
        Page<UserDto> result =  userStore.getPaginatedUsers(pageNo,search);
        boolean hasNext = pageNo+1<result.getTotalPages();
        return new Pagination<>(hasNext,result.getContent());
    }

    public UserDetails getUserDetails(Long userId,CreatedBy eUserType){
        Optional<User> optionalUser = userStore.findById(userId);
        if(optionalUser.isEmpty()){
            throw new DefaultException("User not Found");
        }
        User user = optionalUser.get();

        if(eUserType == CreatedBy.ADMIN && user.getSignupStage()!=SignupStage.STAGEII){
            throw new InCompleteSignUpException("Incompleted SignUp");
        } else if (user.getSignupStage()!=SignupStage.STAGEII) {
            throw new DefaultException("Signup not completed please ask admin to create Account.");
        }

        UserDetails.UserDetailsBuilder userDetailsBuilder = UserDetails.builder();
        userDetailsBuilder
                .name(user.getName())
                .phone(user.getPhone());
        if(user.getUserType() == UserType.CLIENT){
            UserInfo userInfo = userInfoStore.findByUserId(userId);
            userDetailsBuilder
                    .panNo(userInfo.getPanNo())
                    .address(userInfo.getAddress())
                    .aadhaarNo(userInfo.getAadhaarNo());
        }
        if(eUserType == CreatedBy.ADMIN){
            userDetailsBuilder.addLoanBtn(true);
        }
        return userDetailsBuilder.build();
    }
}
