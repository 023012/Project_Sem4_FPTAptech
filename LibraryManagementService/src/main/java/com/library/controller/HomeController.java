package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

//    Client User
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/library")
    public String shop(Model model){
        model.addAttribute("title", "Library");
        return "client/library";
    }


//    Backend Admin
    @RequestMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("title", "Das board");
        return "admin/index";
    }
}
