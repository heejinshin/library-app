package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    public void updateUser(JdbcTemplate jdbcTemplate, UserUpdateRequest request){
        if(userRepository.isUserNotExist(jdbcTemplate, request.getId())) {
            throw new IllegalArgumentException();
        }


    }
}
