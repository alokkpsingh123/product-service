package org.alok.projectservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.alok.projectservice.dto.ProductResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.entity.Product;
import org.alok.projectservice.repository.CategoryRepository;
import org.alok.projectservice.repository.ProductRepository;
import org.alok.projectservice.services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public ProductResponseDto addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        log.debug("Inside add product");
        product = productRepository.save(product);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(product,productResponseDto );
        return productResponseDto;
    }

    @Override
    public Optional<Product> getProductById(String productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        log.debug("Inside get product by id");
        return productRepository.findById(productId);
    }

    @Override
    public Iterable<Product> getAllProduct() {
        log.debug("Inside get all product");
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        log.debug("Inside update product");
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        log.debug("Inside delete product");
        productRepository.deleteById(productId);

    }

    @Override
    public Iterable<Category> getAllCategoriesWithProducts() {

        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            List<Product> productsInCategory = productRepository.findByCategory(category);
            category.setProductList(productsInCategory);
        }

        log.debug("Inside get all categories with products");
        return categories;
    }
}
