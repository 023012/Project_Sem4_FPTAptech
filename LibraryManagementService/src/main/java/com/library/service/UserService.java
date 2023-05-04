package com.library.service;

import com.library.dto.UserDto;
import com.library.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    User save(UserDto userDto);
    User update(User user, MultipartFile imageUser);

    User saveInfo(User user, MultipartFile imageUser);
}