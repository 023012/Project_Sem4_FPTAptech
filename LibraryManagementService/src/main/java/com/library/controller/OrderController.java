package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Order;
import com.library.entity.User;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.BookService;
import com.library.service.OrderService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    private final UserService userService;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @GetMapping("/admin/orders")
    public String order(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("size", orders.size());
        model.addAttribute("title", "Orders");
        return "admin/orders/order";
    }


    @GetMapping("/admin/orders/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        return "admin/orders/order-detail";
    }

//    @GetMapping("/admin/orders/{id}")
//    public String getOrderByID(@PathVariable Long id, Model model){
//        Order order = orderRepository.findById(id).get();
//        if(order != null){
//            model.addAttribute("order", order);
//            return "admin/orders/order";
//        }else {
//            model.addAttribute("error", "Order with code " + id + " is not existed !");
//            return "admin/404";
//        }
//    }

//    @GetMapping("/user")
//    public String getAllOrdersByUser(Model model, Principal principal) {
//        String username = principal.getName();
//        BigInteger userId = userService.getUserIdByUsername(username);
//        List<OrderOfUserDto> ordersOfUser = orderService.getOrdersOfUser(userId);
//        model.addAttribute("ordersOfUser", ordersOfUser);
//        return "orders/userList";
//    }

    @GetMapping("/admin/orders/new")
    public String showOrderCreateForm(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Order order = new Order();
        model.addAttribute("order", order);
        return "admin/orders/order-add";
    }

    @PostMapping("/admin/orders/add")
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/orders/order-add";
        }
        orderService.createOrder(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Order> order = orderRepository.findById(id);
        model.addAttribute("title", "Order edit");
        if(order.isPresent()){
            model.addAttribute("order", order.get());
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "admin/orders/order-edit";
        }else {
            model.addAttribute("message", "Order with code " + id + " is not existed !");
            return "admin/404";
        }
    }

    @PostMapping("/admin/orders/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, @Valid @ModelAttribute("order") Order order,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "admin/orders/order-edit";
        }
        orderService.updateOrder(id, order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";
    }
}
