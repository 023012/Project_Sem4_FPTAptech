package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private String fullName;

    private String email;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private OrderType type;

//    //Paypal get data
//    @Transient
//    private String currency;
//    @Transient
//    private String method;
//    @Transient
//    private String intent;
//    @Transient
//    private String description;


    private int totalDeposit;
    private int totalRent;

    private Date createdAt;
    private Date updatedAt;


    @ManyToOne(
            //cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id"
    )
    private User user;

    public enum OrderStatus{
        PENDING,
        AVAILABLE,
        PROCESSING,
        COMPLETED,
        CANCELED,
        REFUND,
        COMPLAINT
    }

    public enum OrderType{
        PAYPAL,
        VIRTUAL_WALLET,
        DIRECTLY
    }
}
