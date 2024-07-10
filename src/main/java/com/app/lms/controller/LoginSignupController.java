package com.app.lms.controller;

import com.app.lms.dto.*;
import com.app.lms.framework.Session;
import com.app.lms.service.UserService;
import com.app.lms.util.enums.CreatedBy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginSignupController {

    private final UserService userService;

    @PostMapping(value = "/login/v{version_id}",consumes = "application/json",produces = "application/json")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest body, @RequestHeader(required = false) String token, @PathVariable("version_id") Integer versionId){
        LoginResponse response = userService.loginUser(body);
        return new Response<>(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/token/v{version_id}",produces = "application/json")
    public Response<TokenResponse> createUserStageI(@PathVariable("version_id") Integer versionId,@RequestParam(required = true,value = "u") Long userId){
        String token = userService.getUserToken(userId);
        TokenResponse response = new TokenResponse(token);
        return new Response<>(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create/s1/v{version_id}",consumes = "application/json",produces = "application/json")
    public Response<CreateUserResponseStageI> createUserStageI(@Valid @RequestBody CreateUserRequestStageI body, @PathVariable("version_id") Integer versionId){
        CreateUserResponseStageI response = userService.createNewUser(body);
        return new Response<>(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create/s2/v{version_id}",consumes = "application/json",produces = "application/json")
    public Response<Message> createUserStageII(@Valid @RequestBody CreateUserRequestStageII body, @PathVariable("version_id") Integer versionId){
        AuthenticatedUser user = Session.getAuthenticatedUser();
        userService.createNewUser(body,user.getUserId());
        Message message = new Message("User Created Successfully");
        return new Response<>(message);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/reset/password/v{version_id}",consumes = "application/json",produces = "application/json")
    public Response<Message> forgotPassword(@Valid @RequestBody ResetPasswordRequest body, @PathVariable("version_id") Integer versionId,@RequestParam(required = true,value = "u") Long userId){
        userService.forgotPassword(userId, body);
        return new Response<>(new Message("Password Reset Successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list/v{version_id}",produces = "application/json")
    public Pagination<UserDto> getUserList(@RequestParam(required = true,value = "page") String pageNo, @RequestParam(required = false,value = "s") String search, @PathVariable("version_id") Integer versionId){
        return userService.getPaginatedUsers(Integer.valueOf(pageNo),search);
    }

    @GetMapping(value = "/details/v{version_id}",produces = "application/json")
    public Response<UserDetails> getUserDetails(@PathVariable("version_id") Integer versionId){
        AuthenticatedUser user = Session.getAuthenticatedUser();
        Long userId = user.getUserId();
        CreatedBy eUserType = user.getCreatedBy();
        UserDetails userDetails = userService.getUserDetails(userId,eUserType);
        return new Response<>(userDetails);
    }
}
