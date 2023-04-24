package com.library.service;

import com.library.dto.UserDto;
import com.library.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    User save(UserDto userDto);
    User update(User user);
}