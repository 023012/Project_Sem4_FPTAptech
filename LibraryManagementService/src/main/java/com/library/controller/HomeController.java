package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }
    @RequestMapping("/dasboard")
    public String dasboard(Model model){
        model.addAttribute("title", "Das board");
        return "admin/index";
    }
}
