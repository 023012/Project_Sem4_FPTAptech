package com.library.service;

import com.library.entity.Role;
import com.library.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    Service of User
    User saveUser(User user);
    User getUser(String username);
    User findUserByEmail(String email);
    List<User> getUsers();
    User updateUserById(Long id, User user);
    User updateUserByLoggedIn( User user);


    //    Service of Role
    Role saveRole(Role role);


    String addRoleToUser(String email, String roleName);


//    Password reset
    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

//    Change password
    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
