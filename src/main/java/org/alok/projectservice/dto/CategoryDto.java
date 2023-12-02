package org.alok.projectservice.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private String categoryName;
    private int categoryOffer;

    public CategoryDto() {
    }

    public CategoryDto(String categoryName, int categoryOffer) {
        this.categoryName = categoryName;
        this.categoryOffer = categoryOffer;
    }
}
