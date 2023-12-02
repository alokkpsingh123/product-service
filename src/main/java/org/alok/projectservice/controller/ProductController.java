package org.alok.projectservice.controller;

import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.dto.ProductResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.entity.Product;
import org.alok.projectservice.services.CategoryService;
import org.alok.projectservice.services.ProductService;
import org.alok.projectservice.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-product")
    ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(@RequestBody ProductDto productDto){
        try {
            if (productDto == null) {
                throw new IllegalArgumentException("ProductDto cannot be null");
            }

            String categoryName = productDto.getCategoryName();
            Category category = categoryService.getCategoryById(categoryName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with name: " + categoryName));

            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            category.getProductList().add(product);
            product.setCategory(category);
            ProductResponseDto addedProduct = productService.addProduct(product);

            return ResponseEntity.ok(new ApiResponse<>(addedProduct));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new ApiResponse<>("NOT_FOUND", "Category not found: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while adding the product"));
        }

    }

    @GetMapping("/get-product-by-id/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProductById(String productId){
        try {
            if (productId == null) {
                throw new IllegalArgumentException("ProductId cannot be null");
            }

            Optional<Product> product = productService.getProductById(productId);

            return ResponseEntity.ok(new ApiResponse<>(product.get()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }

    @GetMapping("/get-all-product")
    ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProduct(){
        try {

            Iterable<Product> productList = productService.getAllProduct();
            List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
            for(Product product: productList){
                ProductResponseDto productResponseDto = new ProductResponseDto();
                BeanUtils.copyProperties(product, productResponseDto);
                productResponseDtoList.add(productResponseDto);
            }
            return ResponseEntity.ok(new ApiResponse<>(productResponseDtoList));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }


    @PutMapping("/update-product/{productId}")
    ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable String productId, @RequestBody Product product){
        try {
            if (productId == null && product == null) {
                throw new IllegalArgumentException("ProductId or Product cannot be null");
            }

            Optional<Product> productOptional = productService.getProductById(productId);
            Product productSaved = productService.updateProduct(productId, product);

            return ResponseEntity.ok(new ApiResponse<>(productSaved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("INVALID_INPUT", "Invalid input: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }

    @DeleteMapping("/delete-product/{productId}")
    ResponseEntity<ApiResponse< Map<String, Boolean>>> deleteProduct(@PathVariable String productId){
        try {
            if (productId == null) {
                throw new IllegalArgumentException("ProductId cannot be null");
            }

            productService.deleteProduct(productId);
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


    @GetMapping("/get-all-categories-with-products")
    ResponseEntity<ApiResponse<Iterable<Category>>> getAllCategoriesWithProducts(){
        try {

            Iterable<Category> categoryList = productService.getAllCategoriesWithProducts();
            return ResponseEntity.ok(new ApiResponse<>(categoryList));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("INTERNAL_ERROR", "An error occurred while fetching the category"));
        }
    }
}
