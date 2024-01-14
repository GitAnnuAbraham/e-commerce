package com.creativekart.service;

import com.creativekart.model.Cart;
import com.creativekart.model.CartItem;
import com.creativekart.model.Product;
import com.creativekart.model.User;
import com.creativekart.repository.CartRepository;
import com.creativekart.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticateUser(User user, String username, String password) {
        return user != null && user.getPassword().equals(password);
    }

    public void addToCart(User user, Product product) {
        // Ensure user and product are not null
        if (user != null && product != null) {
            // Get or create the user's cart
            Cart cart = user.getCart();
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cart.setCreatedOn(new Date());
            }
            boolean productInCart = false;
            // Check if the product is already in the cart
            List<CartItem> cartItems = cart.getCartItems();
            if(cartItems != null) {
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getProduct().getProductId().equals(product.getProductId())) {
                        // Product is already in the cart, increase quantity
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        productInCart = true;
                        break;
                    }
                }
            } else {
                cartItems = new ArrayList<>();
            }

            if (!productInCart) {
                // Product is not in the cart, create a new cart item
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItems.add(cartItem);
            }

            // Update the cart and save the changes
            cart.setCartItems(cartItems);
            user.setCart(cart);

            //cartRepository.save(cart);

            userRepository.update(user);
        }
    }
}
