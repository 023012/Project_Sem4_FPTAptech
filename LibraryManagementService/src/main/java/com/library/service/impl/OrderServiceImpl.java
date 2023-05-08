package com.library.service.impl;

import com.library.dto.OrderDetailDto;
import com.library.entity.Order;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        String randomCode = RandomString.make(10);
        order.setOrderNumber(randomCode);

        Calendar cal = Calendar.getInstance();
        order.setCreatedAt(cal.getTime());
        order.setUpdatedAt(cal.getTime());

        order.setTotalRent(0);
        order.setTotalDeposit(0);

        order.setStatus(Order.OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getListOrderByUserID(Long userID){
        log.info("Fetching all orders by userID");
        return orderRepository.getAllOrderByUserID(userID);
    }

    @Override
    public OrderDetailDto getOrderDetailByUserID(Long userID, Long orderId){
        Order getOrderDetail = orderRepository.getOrderDetailByUserID(userID, orderId);

        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(getOrderDetail.getOrderId());
        orderDetailDto.setAddress(getOrderDetail.getAddress());
        orderDetailDto.setEmail(getOrderDetail.getEmail());
        orderDetailDto.setFullName(getOrderDetail.getFullName());
        orderDetailDto.setPhoneNumber(getOrderDetail.getPhoneNumber());
        orderDetailDto.setStatus(getOrderDetail.getStatus());
        orderDetailDto.setTotalDeposit(getOrderDetail.getTotalDeposit());
        orderDetailDto.setTotalRent(getOrderDetail.getTotalRent());
        orderDetailDto.setCreatedAt(getOrderDetail.getCreatedAt());
        orderDetailDto.setUpdatedAt(getOrderDetail.getUpdatedAt());

        return orderDetailDto;
    }


    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        if(order == null){
            return "Cannot find Order " +id;
        }else{
            orderRepository.delete(order);
            return "Order "+id+ " has been deleted !";
        }
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Calendar cal = Calendar.getInstance();
        order.setUpdatedAt(cal.getTime());

        Order orderExisted = orderRepository.findById(id).get();
//        orderExisted.setOrderId(order.getOrderId());
        orderExisted.setFullName(order.getFullName());
        orderExisted.setEmail(order.getEmail());
        orderExisted.setPhoneNumber(order.getPhoneNumber());
        orderExisted.setAddress(order.getAddress());
        orderExisted.setStatus(order.getStatus());
        orderExisted.setTotalDeposit(order.getTotalDeposit());
        orderExisted.setTotalRent(order.getTotalRent());


        orderExisted.setUpdatedAt(order.getUpdatedAt());

        orderRepository.save(orderExisted);
        return orderExisted;
    }




}