package org.alok.projectservice.controller;


import org.alok.projectservice.dto.CategoryDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.services.CategoryService;
import org.alok.projectservice.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody CategoryDto categoryDto) {

        try {
            if (categoryDto == null) {
                throw new IllegalArgumentException("CategoryDto cannot be null");
            }

            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            Category addedCategory = categoryService.addCategory(category);

            return ResponseEntity.ok(new ApiResponse<>(addedCategory));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: "
                    + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while adding the category"));
        }
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable String categoryId) {

        try {
            if (categoryId == null) {
                throw new IllegalArgumentException("CategoryId cannot be null");
            }

            Optional<Category> category = categoryService.getCategoryById(categoryId);

            return ResponseEntity.ok(new ApiResponse<>(category.get()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }


    @GetMapping("/get-all-category")
    ResponseEntity<ApiResponse<Iterable<Category>>> getAllCategory() {

        try {

            Iterable<Category> categoryList = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse<>(categoryList));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }

    }

    @PutMapping("/update-category/{categoryId}")
    ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable String categoryId, @RequestBody Category category) {
        try {
            if (categoryId == null && category == null) {
                throw new IllegalArgumentException("CategoryId or Category cannot be null");
            }

            Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);
            Category categorySaved = categoryService.updateCategory(categoryId,category);

            return ResponseEntity.ok(new ApiResponse<>(categorySaved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }

    }

    @DeleteMapping("/delete-category/{categoryId}")
    ResponseEntity<ApiResponse< Map<String, Boolean>>> deleteCategory(@PathVariable String categoryId) {
        try {
            if (categoryId == null) {
                throw new IllegalArgumentException("CategoryId cannot be null");
            }

            categoryService.deleteCategory(categoryId);

            Map<String, Boolean> map = new HashMap<>();
            map.put("Deleted",Boolean.TRUE);

            return ResponseEntity.ok(new ApiResponse<>(map));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }
}
