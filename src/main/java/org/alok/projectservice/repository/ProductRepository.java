package org.alok.projectservice.repository;

import org.alok.projectservice.entity.Category;
import org.alok.projectservice.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByCategory(Category category);
}
