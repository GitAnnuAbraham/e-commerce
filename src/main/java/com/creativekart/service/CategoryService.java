package com.creativekart.service;

import com.creativekart.model.Category;
import com.creativekart.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void deleteCategory(Long categoryId) {
        // Check if the category exists
        Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            // Delete the category if it exists
            categoryRepository.delete(category);
        } else {
            // Handle the case when the category does not exist (optional)
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
    }

    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }


    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
