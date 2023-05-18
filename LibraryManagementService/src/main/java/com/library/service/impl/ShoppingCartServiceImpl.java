package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.CartItem;
import com.library.entity.ShoppingCart;
import com.library.entity.User;
import com.library.repository.CartItemRepository;
import com.library.repository.ShoppingCartRepository;
import com.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartItemRepository itemRepository;

    private final ShoppingCartRepository cartRepository;


    @Override
    public ShoppingCart addItemToCart(Book book, int quantity, User user){
        ShoppingCart cart = user.getShoppingCart();
        if(cart == null ){
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, book.getId());
        if (cartItems == null){
            cartItems = new HashSet<>();
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setTotalRent(quantity * book.getBorrowPrice());
                cartItem.setTotalDeposit(quantity * book.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setBorrowedAt(cartItem.getBorrowedAt());
                cartItem.setReturnedAt(cartItem.getReturnedAt());
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        }else {
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setTotalRent(quantity * book.getBorrowPrice());
                cartItem.setTotalDeposit(quantity * book.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setBorrowedAt(cartItem.getBorrowedAt());
                cartItem.setReturnedAt(cartItem.getReturnedAt());
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalRent(cartItem.getQuantity() * book.getBorrowPrice());
                cartItem.setTotalDeposit(cartItem.getQuantity() * book.getPrice());
                itemRepository.save(cartItem);
            }
        }
        cart.setCartItem(cartItems);

        int totalItems = totalItems(cart.getCartItem());
        int totalRent = totalRent(cart.getCartItem());
        int totalDeposit = totalDeposit(cart.getCartItem());

        cart.setTotalItems(totalItems);
        cart.setTotalRents(totalRent);
        cart.setTotalDeposits(totalDeposit);
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Book book, int quantity, User user){
        ShoppingCart cart = user.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();

        CartItem item = findCartItem(cartItems, book.getId());

        item.setQuantity(quantity);
        item.setTotalRent(quantity * book.getBorrowPrice());

        itemRepository.save(item);

        int totalItems = totalItems(cartItems);
        int totalRent = totalRent(cartItems);
        int totalDeposit = totalDeposit(cartItems);

        cart.setTotalItems(totalItems);
        cart.setTotalRents(totalRent);
        cart.setTotalDeposits(totalDeposit);

        return cartRepository.save(cart);
    }
    @Override
    public ShoppingCart deleteItemFromCart(Book book, User user){
        ShoppingCart cart = user.getShoppingCart();

        Set<CartItem> cartItems = cart.getCartItem();

        CartItem item = findCartItem(cartItems, book.getId());

        cartItems.remove(item);

        itemRepository.delete(item);

        int totalRent = totalRent(cartItems);
        int totalDeposit = totalDeposit(cartItems);
        int totalItems = totalItems(cartItems);

        cart.setCartItem(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalRents(totalRent);
        cart.setTotalDeposits(totalDeposit);

        return cartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long bookId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (item.getBook().getId() == bookId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems){
        int totalItems = 0;
        for(CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private int totalRent(Set<CartItem> cartItems){
        int totalRent = 0;

        for(CartItem item : cartItems){
            totalRent += item.getTotalRent();
        }

        return totalRent;
    }

    private int totalDeposit(Set<CartItem> cartItems){
        int totalDeposit = 0;

        for(CartItem item : cartItems){
            totalDeposit += item.getTotalDeposit();
        }

        return totalDeposit;
    }
}
