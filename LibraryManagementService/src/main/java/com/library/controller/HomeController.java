package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;

import com.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    //    Client User
    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "client/about";
    }

    @GetMapping("/articles")
    public String blog(){
        return "client/blog";
    }

    @GetMapping("/contact-us")
    public String contactUs(){
        return "client/contact";
    }
}
