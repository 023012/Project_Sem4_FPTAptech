package com.library.service;

import com.library.dto.OrderDetailDto;
import com.library.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getListOrderByUserID(Long userID);

    OrderDetailDto getOrderDetailByUserID(Long userID, Long orderId);

    String deleteOrder(Long id);
    Order updateOrder(Long id, Order order);


}
