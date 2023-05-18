package com.library.controller;

import com.library.entity.User;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal){
        if(principal == null){
            return "redirect:/auth/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("title","Profile");

        return "/client/user-profile";
    }

    @PostMapping(value = "/update-userInfo")
    public String updateProfileUser(
            @ModelAttribute("user") User user,
            @ModelAttribute("imageUser") MultipartFile imageUser,
            Model model,
            RedirectAttributes redirectAttributes,
            Principal principal){
        if (principal == null){
            return "redirect:/auth/login";
        }
        User userInfo = userService.saveInfo(user,imageUser);

        redirectAttributes.addFlashAttribute("user", userInfo);
        return "redirect:/auth/account";
    }
}
