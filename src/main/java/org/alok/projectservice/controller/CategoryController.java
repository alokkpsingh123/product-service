package org.alok.projectservice.controller;


import org.alok.projectservice.dto.CategoryDto;
import org.alok.projectservice.dto.CategoryResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.exception.EntityNotFoundException;
import org.alok.projectservice.services.CategoryService;
import org.alok.projectservice.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryDto categoryDto) {


        try {
            if (categoryDto == null) {
                throw new IllegalArgumentException("CategoryDto cannot be null");
            }

            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            Category addedCategory = categoryService.addCategory(category);
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
            BeanUtils.copyProperties(addedCategory, categoryResponseDto);
            return ResponseEntity.ok(categoryResponseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable String categoryId) {

        try {
            if (categoryId == null) {
                throw new IllegalArgumentException("CategoryId cannot be null");
            }

            Category category = categoryService.getCategoryById(categoryId);
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
            BeanUtils.copyProperties(category, categoryResponseDto);

            return ResponseEntity.ok(categoryResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @GetMapping("/get-all-category")
    ResponseEntity<List<CategoryResponseDto>> getAllCategory() {

        try {

            Iterable<Category> categoryList = categoryService.getAllCategory();
            List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();

            for ( Category category : categoryList){
                CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
                BeanUtils.copyProperties(category, categoryResponseDto);
                categoryResponseDtoList.add(categoryResponseDto);
            }


            return ResponseEntity.ok(categoryResponseDtoList);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @PutMapping("/update-category/{categoryId}")
    ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable String categoryId, @RequestBody Category category) {
        try {
            if (categoryId == null && category == null) {
                throw new IllegalArgumentException("CategoryId or Category cannot be null");
            }
            Category categorySaved = categoryService.updateCategory(categoryId, category);
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
            BeanUtils.copyProperties(categorySaved, categoryResponseDto);

            return ResponseEntity.ok(categoryResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @DeleteMapping("/delete-category/{categoryId}")
    ResponseEntity<Map<String, Boolean>> deleteCategory(@PathVariable String categoryId) {
        try {
            if (categoryId == null) {
                throw new IllegalArgumentException("CategoryId cannot be null");
            }

            categoryService.deleteCategory(categoryId);

            Map<String, Boolean> map = new HashMap<>();
            map.put("Deleted", Boolean.TRUE);

            return ResponseEntity.ok(map);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
