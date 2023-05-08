package com.library.repository;

import com.library.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            value = "SELECT * FROM orders s where s.user_id = :userId",
            nativeQuery = true
    )
    List<Order> getAllOrderByUserID(Long userId);

    @Query(
            value = "SELECT s.* " +
                    " FROM orders s " +
                    " where s.user_id = :userId " +
                    " and s.order_id = :orderId",
            nativeQuery = true
    )
    Order getOrderDetailByUserID(Long userId, Long orderId);


}