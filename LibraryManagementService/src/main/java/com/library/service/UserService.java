package com.library.service;

import com.library.dto.UserDto;
import com.library.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    User save(UserDto userDto);
    User update(User user);
}
