package com.creativekart.controller;

import com.creativekart.model.CartItem;
import com.creativekart.model.User;
import com.creativekart.service.CartService;
import com.creativekart.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(sessionUser.getUsername());
        if (user != null) {
            // Get the user's cart items
            List<CartItem> cartItems = cartService.getCartItemsByUser(user);
            model.addAttribute("cartItems", cartItems);

            // Add an empty CartItem to the model
            model.addAttribute("cartItem", new CartItem());
            if(user.getCart() != null) {
                model.addAttribute("userCartId", user.getCart().getCartId());
            }
            return "viewCart";
        } else {
            // Redirect to login if the user is not logged in
            return "redirect:/login";
        }
    }

    @PostMapping("/edit/{cartItemId}")
    public String updateCartItem(HttpSession session, @PathVariable Long cartItemId,
                                 @RequestParam Integer quantity,Model model) {
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(sessionUser.getUsername());
        if (user != null) {
            // Implement logic to retrieve and populate data for editing the cart item
            CartItem cartItem = cartService.findCartItemById(cartItemId);
            cartItem.setQuantity(quantity);
            cartService.updateCartItem(cartItem);
            List<CartItem> cartItems = cartService.getCartItemsByUser(user);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartItem", cartItem);
            return "viewCart"; // Return the view for editing cart items
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId) {
        // Implement logic to delete the cart item
        // ...
        cartService.deleteCartItemById(cartItemId);
        return "redirect:/cart/view";

    }
}
