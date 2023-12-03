package org.alok.projectservice.services;

import org.alok.projectservice.dto.CategoryDto;
import org.alok.projectservice.dto.CategoryResponseDto;
import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CategoryService {

    Category addCategory(Category category);

    Category getCategoryById(String categoryId);

    Iterable<Category> getAllCategory();

    Category updateCategory(String categoryId, Category category);

    void deleteCategory(String categoryId);

}
