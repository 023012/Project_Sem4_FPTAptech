package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {
    private final BookRepository bookRepository;

    public HomeController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //    Client User
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }

//    Book Controller Client
    @GetMapping("/library")
    public String shop(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("title", "Library");
        model.addAttribute("books", books);
        return "client/shop";
    }

    @GetMapping("/book-details/{id}")
    public String bookDetails(@PathVariable("id")  Long id, Model model){
        Optional<Book> book = bookRepository.findById(id);
        model.addAttribute("title", "Single Book");
        model.addAttribute("book", book);
        return "client/single-product";
    }


//    Backend Admin
    @RequestMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("title", "Das board");
        return "admin/index";
    }
}
