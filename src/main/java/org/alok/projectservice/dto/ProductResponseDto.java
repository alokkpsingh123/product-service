package org.alok.projectservice.dto;

import lombok.Data;
import org.alok.projectservice.entity.Category;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ProductResponseDto {
    private String productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private String productBrand;
    private Category category;


    public ProductResponseDto() {
    }


    public ProductResponseDto(String productId, String productName, String productDescription, String productImageUrl, String productBrand, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productBrand = productBrand;
        this.category = category;
    }
}
