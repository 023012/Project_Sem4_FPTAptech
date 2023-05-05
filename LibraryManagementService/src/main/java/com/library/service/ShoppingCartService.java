package com.library.service;

import com.library.entity.Book;
import com.library.entity.ShoppingCart;
import com.library.entity.User;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Book book, int quantity, User user);

    ShoppingCart updateItemInCart(Book book, int quantity, User user);

    ShoppingCart deleteItemFromCart(Book book, User user);
}
