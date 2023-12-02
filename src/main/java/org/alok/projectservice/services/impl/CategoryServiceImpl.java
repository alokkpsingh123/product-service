package org.alok.projectservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.alok.projectservice.dto.CategoryDto;
import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.repository.CategoryRepository;
import org.alok.projectservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {

        log.debug("Inside add Category");
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(String categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + categoryId));
        log.debug("Inside get category by id");
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Iterable<Category> getAllCategory() {
        log.debug("Inside get all category");
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(String categoryId, Category category) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + categoryId));
        log.debug("Inside update category");
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + categoryId));
        log.debug("Inside delete category");
        categoryRepository.deleteById(categoryId);
    }


}
