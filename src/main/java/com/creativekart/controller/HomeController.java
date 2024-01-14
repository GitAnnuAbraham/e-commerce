package com.creativekart.controller;

import com.creativekart.model.Category;
import com.creativekart.model.Product;
import com.creativekart.model.User;
import com.creativekart.service.CategoryService;
import com.creativekart.service.ProductService;
import com.creativekart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        for(Category category : categories) {
            List<Product> latestProducts = productService.getLatestProductsByCategory(category.getCategoryId() ,3);
            category.setProducts(latestProducts);
        }
        model.addAttribute("categories", categories);
        return "home";
    }

    @GetMapping("/category/{categoryId}")
    public String getProductsFromCategory(@PathVariable Long categoryId, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        if(categories != null && !categories.isEmpty()) {
            categories.removeIf(e -> !e.getCategoryId().equals(categoryId));
        }
        model.addAttribute("categories", categories);
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) {
        if(user.getUserId() != null) {
            userService.update(user);
        } else {
            user.setRole("USER");
            userService.save(user);
        }
        return "signup";
    }
}
