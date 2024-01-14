package com.creativekart.service;

import com.creativekart.model.Cart;
import com.creativekart.model.CartItem;
import com.creativekart.model.User;
import com.creativekart.repository.CartItemRepository;
import com.creativekart.repository.CartRepository;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsByUser(User user) {
        // Retrieve the user's cart
        Cart cart = cartRepository.findByUser(user);

        if (cart != null) {
            // Retrieve cart items associated with the cart
            return cartItemRepository.findByCart(cart);
        }

        // Return an empty list if the user has no cart or cart items
        return Collections.emptyList();
    }

    public CartItem findCartItemById(Long cartItemId) {
        return cartItemRepository.findCartItemById(cartItemId);
    }

    public void updateCartItem(CartItem cartItem) {
        cartItemRepository.update(cartItem);
    }

    public void deleteCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findCartItemById(cartItemId);
        if(cartItem != null) {
                cartItemRepository.delete(cartItem);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
