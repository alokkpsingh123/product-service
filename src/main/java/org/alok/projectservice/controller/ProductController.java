package org.alok.projectservice.controller;

import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.dto.ProductResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.exception.EntityNotFoundException;
import org.alok.projectservice.services.ProductService;
import org.alok.projectservice.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

//    @Autowired
//    CategoryService categoryService;

    @PostMapping("/add-product")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductDto productDto) {
        try {
            ProductResponseDto addedProduct = productService.addProduct(productDto);
            return ResponseEntity.ok(addedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @GetMapping("/get-product-by-id/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String productId) {
        try {
            if (productId == null) {
                throw new IllegalArgumentException("ProductId cannot be null");
            }

            return ResponseEntity.ok(productService.getProductById(productId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/get-all-product")
    ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        try {

            return ResponseEntity.ok(productService.getAllProduct());

        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @PutMapping("/update-product/{productId}")
    ResponseEntity<ProductResponseDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        try {
            if (productId == null && productDto == null) {
                throw new IllegalArgumentException("ProductId or Product cannot be null");
            }
            return ResponseEntity.ok(productService.updateProduct(productId, productDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/delete-product/{productId}")
    ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable String productId) {
        try {
            if (productId == null) {
                throw new IllegalArgumentException("ProductId cannot be null");
            }

            productService.deleteProduct(productId);
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


    @GetMapping("/get-all-categories-with-products")
    ResponseEntity<List<Category>> getAllCategoriesWithProducts() {
        try {

            List<Category> categoryList = productService.getAllCategoriesWithProducts();
            return ResponseEntity.ok(categoryList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/filter-products/{input}")
    public ResponseEntity<List<ProductResponseDto>> filterProducts(@PathVariable String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                throw new IllegalArgumentException("Input cannot be null or empty");
            }

            List<ProductResponseDto> filteredProducts = productService.filterProducts(input);
            return ResponseEntity.ok(filteredProducts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
