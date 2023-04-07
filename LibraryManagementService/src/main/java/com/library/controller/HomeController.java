package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
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

    //    Client User
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "client/about";
    }

    @GetMapping("/books")
    public String shop(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("title", "Books");
        model.addAttribute("books", books);
        return "client/book-list";
    }

    @GetMapping("/articles")
    public String blog(){
        return "client/blog";
    }

    @GetMapping("/contact-us")
    public String contactUs(){
        return "client/contact";
    }


//    Backend Admin
    @RequestMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("title", "Das board");
        return "admin/index";
    }

    @GetMapping("/categories")
    public String category(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "admin/category";
    }
}
