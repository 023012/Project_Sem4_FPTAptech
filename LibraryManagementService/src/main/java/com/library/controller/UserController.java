package com.library.controller;

import com.library.dto.UserDto;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@EnableScheduling
@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    //    GetAllUsers
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getUsers(){
//        return ResponseEntity.ok().body(userService.getUsers());
//    }
//    @GetMapping("/admin/users")
//    public String user(Model model) {
//        List<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
//        model.addAttribute("size", users.size());
//        model.addAttribute("title", "Users");
//        return "admin/users/user";
//    }

    //    Backend Controller
    @RequestMapping(value = "/admin/users/new")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add new user");
        return "admin/users/user-add";
    }

    @PostMapping("/admin/users/add")
    public String createUser(@Validated @ModelAttribute("user")
                             User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/users/user-add";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "admin/users/user-edit";
    }

    @PostMapping("/admin/users/edit/{id}")
    public String updateCategory(@PathVariable("id") long user_id,
                                 @Valid @ModelAttribute("book") User user,
                                 @RequestParam("imageUser") MultipartFile imageUser,
                                 BindingResult result) {
        if (result.hasErrors()) {
            user.setId(user_id);
            return "admin/users/user-edit";
        }
//        user.setPassword(encoder.encode(user.getPassword()));
        userService.update(user,imageUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/users";
    }

//    Client Controller

    @GetMapping("/library/users/profile")
    public String getProfile(Model model){
        return "client/user-profile";
    }
}
