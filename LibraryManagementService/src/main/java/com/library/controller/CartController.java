package com.library.controller;

import com.library.entity.*;
import com.library.repository.BookRepository;
import com.library.repository.OrderRepository;
import com.library.service.OrderItemService;
import com.library.service.ShoppingCartService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Calendar;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderItemService orderItemService;

    private final ShoppingCartService cartService;

//    @GetMapping("/library/my")
//    public String cart(Model model, Principal principal, HttpSession session) {
//        if(principal != null){
//            return "redirect:/auth/login";
//        }
//        String email = principal.getName();
//        User user = userService.findByEmail(email);
//        ShoppingCart shoppingCart = user.getShoppingCart();
//        if (shoppingCart == null){
//            model.addAttribute("check", "No item in your cart");
//        }
//        session.setAttribute("totalItems",shoppingCart.getTotalItems());
//        model.addAttribute("totalDeposit", shoppingCart.getTotalDeposit());
//        model.addAttribute("totalRent",shoppingCart.getTotalRent());
//        model.addAttribute("title", "My Books");
//        return "client/cart";
//    }

    @PostMapping("/library/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long bookId,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                Principal principal,
                                HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        Book book = bookRepository.findById(bookId).get();
        String email = principal.getName();
        User user = userService.findByEmail(email);

        ShoppingCart cart = cartService.addItemToCart(book, quantity, user);
        return "redirect:" + request.getHeader("Referer");
    }


    @RequestMapping(value = "/library/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long bookId,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        } else {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            Book book = bookRepository.findById(bookId).get();
            ShoppingCart cart = cartService.updateItemInCart(book, quantity, user);

            model.addAttribute("shoppingCart", cart);
            return "redirect:/library/cart";
        }
    }

    @RequestMapping(value = "/library/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long bookId,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        } else {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            Book book = bookRepository.findById(bookId).get();
            ShoppingCart cart = cartService.deleteItemFromCart(book, user);

            model.addAttribute("shoppingCart", cart);
            return "redirect:/library/cart";
        }
    }
}
