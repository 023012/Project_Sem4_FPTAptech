package com.library.service.impl;

import com.library.dto.UserDto;
import com.library.entity.ERole;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        //add role
        Role role = roleRepository.findByName(ERole.ROLE_USER);
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existingUser = findById(user.getId());
        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setImage(user.getImage());
        existingUser.setAddress(user.getAddress());
        return userRepository.save(existingUser);
    }
}