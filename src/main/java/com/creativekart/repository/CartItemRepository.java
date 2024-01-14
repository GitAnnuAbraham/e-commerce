package com.creativekart.repository;

import com.creativekart.model.Cart;
import com.creativekart.model.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CartItem> findByCart(Cart cart) {
        return entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart = :cart", CartItem.class)
                .setParameter("cart", cart)
                .getResultList();
    }

    public CartItem findCartItemById(Long cartItemId) {
        return entityManager.find(CartItem.class, cartItemId);
    }

    public void update(CartItem cartItem) {
        entityManager.merge(cartItem);
    }

    public void delete(CartItem cartItem) {
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        entityManager.remove(cartItem);
    }
}
