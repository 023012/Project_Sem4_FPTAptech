package com.library.service.impl;

import com.library.dto.UserDto;
import com.library.entity.ERole;
import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import com.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ImageUpload imageUpload;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        user.setVirtualWallet(50000);

        //add role
        Role role = roleRepository.findByName(ERole.ROLE_USER);
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        return userRepository.save(user);
    }

    @Override
    public User update(User user, MultipartFile imageUser) {
        try {
            User existingUser = findById(user.getId());
            if (existingUser == null) {
                return null;
            }

            if (imageUser != null && !imageUser.isEmpty()) {
                if (imageUpload.checkExisted(imageUser) == false) {
                    imageUpload.uploadImage(imageUser);
                }
                existingUser.setImage(Base64.getEncoder().encodeToString(imageUser.getBytes()));
            }
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setVirtualWallet(user.getVirtualWallet());
            existingUser.setStatus(user.getStatus());
            return userRepository.save(existingUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User saveInfo(User user, MultipartFile imageUser) {
        try {

            User userInfo = userRepository.findByEmail(user.getEmail());

            userInfo.setFirstname(user.getFirstname());
            userInfo.setLastname(user.getLastname());

            if (imageUser != null && !imageUser.isEmpty()) {
                if (imageUpload.checkExisted(imageUser) == false) {
                    imageUpload.uploadImage(imageUser);
                }
                userInfo.setImage(Base64.getEncoder().encodeToString(imageUser.getBytes()));
            }

            userInfo.setPhone(user.getPhone());
            userInfo.setAddress(user.getAddress());
            return userRepository.save(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}