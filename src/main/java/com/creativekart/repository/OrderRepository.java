package com.creativekart.repository;

import com.creativekart.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public List<Order> fetchUserOrders(Long userId) {
        return entityManager.createQuery("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product p WHERE o.user.userId = :userId",
                        Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Order> fetchAllOrders() {
        return entityManager.createQuery("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product p",
                        Order.class)
                .getResultList();
    }

    public void updateOrderStatus(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        order.setStatus("COMPLETED");
        entityManager.merge(order);
    }
}
