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
//    List<Order> exportOrderToExcel(HttpServletResponse response) throws IOException;
//    List<Order> exportSingleOrderToExcel(HttpServletResponse response, List<Order> orderList) throws  IOException;
//    List<OrderOfUserDto> getListOrderByUserID_InYear(long userID, int year);
//    List<OrderUserInMonthDto> getListTotalByUserID_InYear(long userId, int year);
//    OrderDetailDto getOrderDetailByUserID(Long userID, String orderId);
//    List<OrderTotalInYearDto> getOrderTotalInYear(int year);
}
