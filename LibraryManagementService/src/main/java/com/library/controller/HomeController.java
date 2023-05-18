package com.library.controller;

import com.library.entity.*;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class    HomeController {


    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    //    Client User
    @RequestMapping(value = {"/index", "/"})
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal != null){
            session.setAttribute("email",principal.getName());
            User user = userService.findByEmail(principal.getName());
//            Set<Role> role = user.getRoles();
//            session.setAttribute("role",role);
        }else {
            session.removeAttribute("email");
        }
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
        List<Book> books = bookRepository.findAll();
        model.addAttribute("title", "Books");
        model.addAttribute("books",books);
        return "client/book-list";
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
    @GetMapping("/library/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null){
            return "redirect:/auth/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null){
            model.addAttribute("check", "No item in your cart");
        }
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("depositTotal", shoppingCart.getTotalDeposits());
        model.addAttribute("rentTotal",shoppingCart.getTotalRents());
        model.addAttribute("title", "Shopping cart");
        model.addAttribute("shoppingCart", shoppingCart);
        return "client/cart";
    }


    //    Backend Admin
    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal, HttpSession session) {
        if (principal != null){
            session.setAttribute("email",principal.getName());
            User user = userService.findByEmail(principal.getName());
        }else {
            session.removeAttribute("email");
        }
        model.addAttribute("title", "Dashboard");
        return "/admin/index";
    }

    @GetMapping("/admin/users")
    public String user(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("size", users.size());
        model.addAttribute("title", "Users");
        return "admin/users/user";
    }

}