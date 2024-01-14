package com.creativekart.controller;

import com.creativekart.model.Category;
import com.creativekart.model.Order;
import com.creativekart.model.Product;
import com.creativekart.model.User;
import com.creativekart.service.CategoryService;
import com.creativekart.service.OrderService;
import com.creativekart.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/home")
    public String adminHome(HttpSession session, Model model) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            return "redirect:/access-denied";
        }
        List<Order> orderList = orderService.fetchAllOrders();
        model.addAttribute("orders", orderList);
        return "adminHome";
    }

    @GetMapping("/manage-categories")
    public String manageCategories(Model model, HttpSession session, @RequestParam(required = false) Long categoryId) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            return "redirect:/access-denied";
        }

        // Load existing categories
        Iterable<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        // If categoryId is provided, load the category for editing
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            model.addAttribute("category", category);
        } else {
            // Add an empty category object if none exists (for form submission)
            model.addAttribute("category", new Category());
        }

        return "manageCategories";
    }

    @PostMapping("/manage-categories")
    public String saveCategory(@ModelAttribute Category category, HttpServletRequest request) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(request.getSession())) {
            return "redirect:/access-denied";
        }

        // Check if categoryId is present to determine create or update
        if (category.getCategoryId() != null) {
            // Category already has an ID, perform an update
            categoryService.updateCategory(category);
            request.setAttribute("successMessage", "Category updated successfully");
        } else {
            // Category doesn't have an ID, perform a create
            categoryService.saveCategory(category);
            request.setAttribute("successMessage", "Category saved successfully");
        }

        // Redirect to the manage-categories page
        return "redirect:/admin/manage-categories";
    }

    @GetMapping("/edit-category")
    public String editCategory(@RequestParam Long categoryId, Model model, HttpSession session) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            //return "redirect:/access-denied";
        }

        // Load the category to edit
        Category category = categoryService.getCategoryById(categoryId);

        // Load existing categories
        Iterable<Category> categories = categoryService.getAllCategories();

        // Add data to the model
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);

        return "manageCategories";
    }

    @GetMapping("/delete-category")
    public String deleteCategory(@RequestParam Long categoryId, HttpSession session) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            return "redirect:/access-denied";
        }

        // Delete the category by ID
        categoryService.deleteCategory(categoryId);

        // Redirect to the manage-categories page with a success message
        return "redirect:/admin/manage-categories?successMessage=Category deleted successfully";
    }

    @GetMapping("/manage-products")
    public String addProductForm(Model model, HttpSession session, @RequestParam(required = false) Long productId) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            return "redirect:/access-denied";
        }

        // Load existing categories for the dropdown
        Iterable<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        // Get product details and add to the model
        List<Product> productDetails = productService.getProductDetails();
        model.addAttribute("productDetails", productDetails);

        // If categoryId is provided, load the category for editing
        if (productId != null) {
            Product product = productService.getProductById(productId);
            model.addAttribute("product", product);
        } else {
            // Add an empty product object for form submission
            model.addAttribute("product", new Product());
        }
        return "manageProducts";
    }

    @PostMapping("/manage-products")
    public String saveProduct(@ModelAttribute Product product, HttpServletRequest request, HttpSession session) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            return "redirect:/access-denied";
        }
        if(product.getProductId() != null) {
            // Save the product
            productService.updateProduct(product);
            request.setAttribute("successMessage", "Product updated successfully");
        } else {

            Category category = categoryService.getCategoryById(product.getCategory().getCategoryId());
            product.setCategory(category);

            // Save the product
            productService.saveProduct(product);
            request.setAttribute("successMessage", "Product saved successfully");
        }

        // Redirect to the add-product page with a success message
        return "redirect:/admin/manage-products";
    }

    @GetMapping("/delete-product")
    public String deleteProduct(@RequestParam Long productId, HttpSession session) {
        // Check if the user is an admin based on the session attribute
        if (!isAdmin(session)) {
            // Handle unauthorized access
            return "redirect:/access-denied";
        }

        // Delete the product by ID
        productService.deleteProduct(productId);

        // Redirect to the manage-products page with a success message
        return "redirect:/admin/manage-products?successMessage=Product deleted successfully";
    }


    private boolean isAdmin(HttpSession session) {
        // Check the session attribute to determine if the user is an admin
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }
}
