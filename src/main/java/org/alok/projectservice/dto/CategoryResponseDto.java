package org.alok.projectservice.dto;

import lombok.Data;
import org.alok.projectservice.entity.Product;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseDto {
    private String categoryName;
    private int categoryOffer;
    private List<Product> productList = new ArrayList<>();

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(String categoryName, int categoryOffer, List<Product> productList) {
        this.categoryName = categoryName;
        this.categoryOffer = categoryOffer;
        this.productList = productList;
    }
}
