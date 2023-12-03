package org.alok.projectservice.services;

import org.alok.projectservice.dto.CategoryDto;
import org.alok.projectservice.dto.ProductDto;
import org.alok.projectservice.dto.ProductResponseDto;
import org.alok.projectservice.entity.Category;
import org.alok.projectservice.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    ProductResponseDto addProduct(ProductDto productDto);

    ProductResponseDto getProductById(String productId);

    List<ProductResponseDto> getAllProduct();

    ProductResponseDto updateProduct(String productId, ProductDto productDto);

    void deleteProduct(String productId);

    List<Category> getAllCategoriesWithProducts();


}
