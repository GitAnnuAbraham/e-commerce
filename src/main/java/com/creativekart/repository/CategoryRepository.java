package com.creativekart.repository;

import com.creativekart.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Category category) {
        entityManager.persist(category);
    }

    public Category findById(Long categoryId) {
        return entityManager.find(Category.class, categoryId);
    }

    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    public void update(Category category) {
        entityManager.merge(category);
    }

    public void delete(Category category) {
        entityManager.remove(entityManager.contains(category) ? category : entityManager.merge(category));
    }
}
