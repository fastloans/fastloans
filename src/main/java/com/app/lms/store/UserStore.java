package com.app.lms.store;

import com.app.lms.dto.UserDto;
import com.app.lms.entity.User;
import com.app.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public Optional<User> findUserByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Page<UserDto> getPaginatedUsers(Integer pageNo,String search){
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC,"id");
        if(search!=null){
            return userRepository.getPaginatedUsers(search,pageable);
        }
        return userRepository.getPaginatedUsers(pageable);
    }
}
