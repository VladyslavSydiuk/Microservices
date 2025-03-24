package com.example.model.dto;

import com.example.model.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Integer price;
    private String productDescription;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
