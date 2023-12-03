package org.alok.projectservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.dto.ProductResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.entity.Product;
import org.alok.projectservice.exception.EntityNotFoundException;
import org.alok.projectservice.repository.CategoryRepository;
import org.alok.projectservice.repository.ProductRepository;
import org.alok.projectservice.services.CategoryService;
import org.alok.projectservice.services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public ProductResponseDto addProduct(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null");
        }

        String categoryName = productDto.getCategoryName();
        Category category = categoryService.getCategoryById(categoryName);
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setCategory(category);
        category.getProductList().add(product);
        categoryService.updateCategory(category.getCategoryName(),category);

        Product savedProduct = productRepository.save(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(savedProduct, productResponseDto);
        productResponseDto.setCategory(category);

        return productResponseDto;
    }


    @Override
    public ProductResponseDto getProductById(String productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        log.debug("Inside get product by id");
        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(productRepository.findById(productId).get(), productResponseDto);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        log.debug("Inside get all product");

        Iterable<Product> productList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto();
            BeanUtils.copyProperties(product, productResponseDto);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto updateProduct(String productId, ProductDto productDto) {
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        log.debug("Inside update product");
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        product = productRepository.save(product);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(product, productResponseDto);
        return productResponseDto;
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        log.debug("Inside delete product");
        productRepository.deleteById(productId);

    }

    @Override
    public List<Category> getAllCategoriesWithProducts() {

        Iterable<Category> categories = categoryService.getAllCategory();
        List<Category> categoryList = new ArrayList<>();

        for (Category category : categories) {
            List<Product> productsInCategory = productRepository.findByCategory(category);
            category.setProductList(productsInCategory);
            categoryList.add(category);
        }

        log.debug("Inside get all categories with products");
        return categoryList;
    }
}
