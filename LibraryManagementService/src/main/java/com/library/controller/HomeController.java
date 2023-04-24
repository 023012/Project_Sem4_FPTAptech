package com.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {


    //    Client User
    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/library/about")
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        return "client/about";
    }

    @GetMapping("/library/books")
    public String allBook(Model model) {
        model.addAttribute("title", "Books");
        return "/client/book-list";
    }

    @GetMapping("/library/articles")
    public String blog(Model model) {
        model.addAttribute("Articles");
        return "client/articles";
    }

    @GetMapping("/library/contact-us")
    public String contactUs(Model model) {
        model.addAttribute("title", "Contact Us");
        return "client/contact";
    }

    // Giỏ đựng sách
    @GetMapping("/library/my-books")
    public String myBooks(Model model) {
        model.addAttribute("title", "My Books");
        return "client/cart";
    }


    //    Backend Admin
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Dashboard");
        return "/admin/index";
    }
}