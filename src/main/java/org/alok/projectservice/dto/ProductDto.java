package org.alok.projectservice.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private String productBrand;
    private String categoryName;


    public ProductDto() {
    }

    public ProductDto(String productId, String productName, String productDescription, String productImageUrl, String productBrand, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productBrand = productBrand;
        this.categoryName = categoryName;
    }
}
