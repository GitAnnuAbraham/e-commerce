package com.creativekart.repository;

import com.creativekart.model.Cart;
import com.creativekart.model.CartItem;
import com.creativekart.model.Category;
import com.creativekart.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Cart cart) {
        entityManager.persist(cart);
    }

    public Cart findByUser(User user) {

        User userProfile = entityManager.find(User.class, user.getUserId());
        if(userProfile != null && user.getCart() != null) {
            return user.getCart();
        }
        return null;
//        return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.userId = :userId", Cart.class)
//                .setParameter("userId", user.getUserId())
//                .getSingleResult();
    }

    public Cart findCartById(Long cartId) {
        return entityManager.find(Cart.class, cartId);
    }

    public void delete(Long cartId) {
        Cart cart = findCartById(cartId);
        for(CartItem cartItem : cart.getCartItems()) {
            //cart.getCartItems().remove(cartItem);
            entityManager.remove(cartItem);
        }
        entityManager.remove(cart);
    }

}