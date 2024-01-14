package com.creativekart.service;

import com.creativekart.model.Product;
import com.creativekart.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct(Long productId) {
        // Check if the product exists
        Product product = productRepository.findById(productId);
        if (product != null) {
            // Delete the product if it exists
            productRepository.delete(product);
        } else {
            // Handle the case when the category does not exist (optional)
            throw new EntityNotFoundException("Category not found with id: " + productId);
        }
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProductDetails() {
        return productRepository.getProductDetails();
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }


    public List<Product> getLatestProductsByCategory(Long categoryId, int limit) {
        return productRepository.getLatestProductsByCategory(categoryId, limit);
    }
}
