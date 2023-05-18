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
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final BookService bookService;

//    Client fe
    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal){
        if (principal == null){
            return "redirect:/auth/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user.getPhone().trim().isEmpty());
        return null;
    }


//    Admin be
    @GetMapping("/admin/orders")
    public String order(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("size", orders.size());
        model.addAttribute("title", "Orders");
        return "/admin/orders/order";
    }

    @GetMapping("/admin/orders/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("title", "Order Detail");
        return "admin/orders/order-detail";
    }

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


//    @GetMapping("/orders")
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }
//
//    @GetMapping("/orders/user")
//    public List<Order> getOrdersByUserID(@RequestParam("userId") Long userId){
//        return orderService.getListOrderByUserID(userId);
//    }
////

//    @GetMapping("/order/{id}")
//    public ResponseEntity<?> getOrderByID(@PathVariable String id){
//        if(orderRepository.findById(id) == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with code "+id+" is not existed !");
//        }else {
//            return ResponseEntity.ok().body(orderRepository.findById(id));
//        }
//    }
//
//    @PostMapping("/orders/add")
//    public Order createOrder(@RequestParam("userId") Long userId ,@RequestBody Order order) {
//        User userFind = userRepository.findById(userId).get();
//        order.setUser(userFind);
//        System.out.println(order);
//        return orderService.createOrder(order);
//    }
//
//    @DeleteMapping("/orders/delete/{id}")
//    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
//        return ResponseEntity.ok(orderService.deleteOrder(id));
//    }
//
//    @PutMapping("/orders/save")
//    public ResponseEntity<Order> updateOrder(@RequestParam("orderID") String orderID ,
//                                             @RequestBody Order order) {
//        order = orderService.updateOrder(orderID, order);
//        return ResponseEntity.ok(order);
//    }


//    @GetMapping("/orders/user-by-month")
//    public ResponseEntity<?> getListOrderOfUserByMonth(@RequestParam("userId") Long userId,
//                                                       @RequestParam("year") int year){
//        return ResponseEntity.ok().body(orderService.getListOrderByUserID_InYear(userId, year));
//    }
//
//    @GetMapping("/orders/user-total-year")
//    public ResponseEntity<?> getTotalOrderOfUserInYear(@RequestParam("userId") Long userId,
//                                                       @RequestParam("year") int year){
//        return ResponseEntity.ok().body(orderService.getListTotalByUserID_InYear(userId, year));
//    }
//
//    @GetMapping("/orders/total-in-year")
//    public ResponseEntity<?> getTotalOrderInYear(@RequestParam("year") int year){
//        return ResponseEntity.ok().body(orderService.getOrderTotalInYear( year));
//    }

//    @GetMapping("/orders/export-to-excel")
//    public void exportToExcelAllOrderData(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=Orders_Information.xlsx";
//        response.setHeader(headerKey, headerValue);
//        orderService.exportOrderToExcel(response);
//    }
//
//    @GetMapping("/orders/export-to-excel-single")
//    public void exportToExcelAllOrderDataOfSingleAccount(@RequestParam("userId") Long userId,
//                                                         HttpServletResponse response) throws IOException{
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=Your_Orders_Information.xlsx";
//        response.setHeader(headerKey, headerValue);
//        List<Order> orderList = orderService.getListOrderByUserID(userId);
//        orderService.exportSingleOrderToExcel(response, orderList);
//    }
//
//    @GetMapping("/orders/export-excel-single")
//    public void exportToExcelAllOrderDataOfSingleAccount(HttpServletResponse response, List<Order> orders) throws IOException{
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=Your_Orders_Information.xlsx";
//        response.setHeader(headerKey, headerValue);
//        orderService.exportSingleOrderToExcel(response, orders);
//    }


}
