package com.library.service.impl;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.entity.token.PasswordResetToken;
import com.library.repository.PasswordResetTokenRepository;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    //    Service of User
    @Override
    public User saveUser(User user) {
        //Account auto has role 'Member' whenever created
        Role role00 = roleRepository.findByName("MEMBER");
        Role role01 = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role00);
        roles.add(role01);
        user.setRoles(roles);
        user.setVirtualWallet(50000);
        if(user.getAvatar() == null){
            user.setAvatar("https://cdn2.vectorstock.com/i/1000x1000/23/81/default-avatar-profile-icon-vector-18942381.jpg");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user {} to the database", user.getName());
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(Long id, User user) {
        User userUpdate = userRepository.findById(id).get();
        userUpdate.setName(user.getName());
        userUpdate.setUsername(user.getUsername());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPhoneNumber(user.getPhoneNumber());
        userUpdate.setAddress(user.getAddress());
        userUpdate.setAvatar(user.getAvatar());
        userUpdate.setStatus(user.getStatus());
        userUpdate.setVirtualWallet(user.getVirtualWallet());

        userRepository.save(userUpdate);
        return userUpdate;
    }

    //    Service of Role
    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public String addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        if(user ==  null){
            return "Cannot find User with email: " + email + "!";
        }if(role == null){
            return "Role "+roleName+" is not existed !";
        } if (user != null && role != null){
            if(user.getRoles().contains(role)){
                return "User "+ email + " has already have role " + roleName + " !";
            }
            user.getRoles().add(role);
            log.info("Adding role " + roleName + " to " + email + " successfully!");
            return "Adding role " + roleName + " to " + email + " successfully!";
        }
        return "";
    }


    @Override
    public User updateUserByLoggedIn( User user) {
        user.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setAvatar(user.getAvatar());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setAddress(user.getAddress());
        user.setStatus(user.getStatus());
        user.setVirtualWallet(user.getVirtualWallet());
        user.setEmail(user.getEmail());

        userRepository.save(user);
        return user;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken
                = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null) {
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "token has expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
