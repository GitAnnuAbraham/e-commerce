package com.creativekart.repository;

import com.creativekart.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Product product) {
        entityManager.persist(product);
    }

    public Product findById(Long categoryId) {
        return entityManager.find(Product.class, categoryId);
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT c FROM Product c", Product.class)
                .getResultList();
    }

    public void update(Product product) {
        entityManager.merge(product);
    }

    public void delete(Product product) {
        entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
    }

    public List<Product> getProductDetails() {
//        return entityManager.createQuery(
//                "SELECT c.name, p.name, p.description, p.price, p.stockQuantity " +
//                        "FROM Product p JOIN FETCH p.category c"
//        ).getResultList();
        return entityManager.createQuery(
                "SELECT p FROM Product p JOIN FETCH p.category", Product.class
        ).getResultList();
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    public List<Product> getLatestProductsByCategory(Long categoryId, int limit) {
        String query = "SELECT p FROM Product p WHERE p.category.categoryId = :categoryId ORDER BY p.productId DESC";
        return entityManager.createQuery(query, Product.class)
                .setParameter("categoryId", categoryId)
                .setMaxResults(limit)
                .getResultList();
    }
}
