package com.library.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oder_item_id")
    private Long id;
    private int quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//value="2016-08-01"
    private Date borrowedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnedAt;

    private int totalRent;
    private int totalDeposit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private ShoppingCart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

}
