package com.app.lms.store;

import com.app.lms.entity.UserInfo;
import com.app.lms.exception.DefaultException;
import com.app.lms.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoStore {

    private final UserInfoRepository userInfoRepository;

    public UserInfo save(UserInfo userInfo){
        return userInfoRepository.save(userInfo);
    }

    public UserInfo findByUserId(Long userId){
        return userInfoRepository.findById(userId).orElseThrow(()->new DefaultException("User Info not exists"));
    }
}
