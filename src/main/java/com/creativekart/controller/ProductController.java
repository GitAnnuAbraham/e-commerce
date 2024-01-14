package com.creativekart.controller;

import com.creativekart.model.Product;
import com.creativekart.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.creativekart.service.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(sessionUser.getUsername());
        if (user == null) {
            // User not logged in, redirect to login page
            return "redirect:/login";
        }

        // Get the product
        Product product = productService.getProductById(productId);

        // Add the product to the user's cart
        userService.addToCart(user, product);

        // You can redirect to a success page or the product listing page
        return "redirect:/cart/view";
    }

}
