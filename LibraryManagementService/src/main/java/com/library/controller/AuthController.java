package com.library.controller;

import com.library.dto.UserDto;
import com.library.entity.User;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping("/index")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        model.addAttribute("title","Home Page");
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("title","Register");
        model.addAttribute("userDto",new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult result,
                               Model model) {
        try{
            if (result.hasErrors()){
                model.addAttribute("userDto", userDto);
                result.toString();
                return "register";
            }
            String email = userDto.getEmail();
            User user = userService.findByEmail(email);
            if (user != null){
                model.addAttribute("userDto", userDto);
                System.out.println("user not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (userDto.getPassword().equals(userDto.getRepeatPassword())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userService.save(userDto);
                System.out.println("success");
                model.addAttribute("userDto",userDto);
                model.addAttribute("success", "Registration successfully!!!");
            }else{
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "redirect:/auth/login";
    }
}
