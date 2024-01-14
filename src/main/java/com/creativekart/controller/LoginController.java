package com.creativekart.controller;

import com.creativekart.model.User;
import com.creativekart.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.getUserByUsername(username);
        if (user != null && userService.authenticateUser(user, username, password)) {
            if ("ADMIN".equals(user.getRole())) {
                session.setAttribute("user", user);
                return "redirect:/admin/home";
            } else if ("USER".equals(user.getRole())) {
                session.setAttribute("user", user);
                return "redirect:/";
            }
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session on logout
        session.invalidate();
        return "redirect:/login";
    }

}
