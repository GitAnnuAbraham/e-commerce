package com.creativekart.service;

import com.creativekart.model.*;
import com.creativekart.repository.CartRepository;
import com.creativekart.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    public void createOrder(Long cartId) {
        Cart cart = cartRepository.findCartById(cartId);
        Order order = new Order();
        order.setStatus("PLACED");
        order.setOrderDate(new Date());
        List<CartItem> cartItemList = cart.getCartItems();
        List<OrderItem> orderItemList = new ArrayList<>();
        long totalAmount = 0l;
        for(CartItem cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
            totalAmount = totalAmount + (orderItem.getQuantity() * orderItem.getQuantity());
        }
        order.setTotalAmount(totalAmount);
        order.setUser(cart.getUser());

        User user = cart.getUser();
        cartRepository.delete(cart.getCartId());
        user.getCart().setCartItems(null);
        user.setCart(null);

        order.setOrderItems(orderItemList);
        orderRepository.save(order);
    }

    public List<Order> fetchUserOrders(Long userId) {
        List<Order> orderList = orderRepository.fetchUserOrders(userId);
        if(orderList == null && (orderList !=null && !orderList.isEmpty())) {
            orderList = Collections.emptyList();
        }
        return orderList;
    }

    public List<Order> fetchAllOrders() {
        List<Order> orderList = orderRepository.fetchAllOrders();
        if(orderList == null && (orderList !=null && !orderList.isEmpty())) {
            orderList = Collections.emptyList();
        }
        return orderList;
    }

    public void updateOrder(Long orderId) {
        orderRepository.updateOrderStatus(orderId);
    }
}
