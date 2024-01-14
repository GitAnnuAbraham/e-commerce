package com.creativekart.controller;

import com.creativekart.model.Order;
import com.creativekart.model.User;
import com.creativekart.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/checkout/{cartId}")
    public String checkOut(@PathVariable(required = true) Long cartId) {
        orderService.createOrder(cartId);
        return "redirect:/order/viewUserOrder";
    }

    @GetMapping("/viewUserOrder")
    public String viewOrders(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        List<Order> orderList = orderService.fetchUserOrders(user.getUserId());
        model.addAttribute("orders", orderList);
        return "viewOrders";
    }

    @GetMapping("/updateStatus/{orderId}")
    public String updateStatus(@PathVariable(required = true) Long orderId) {
        orderService.updateOrder(orderId);
        return "redirect:/admin/home";
    }
}
