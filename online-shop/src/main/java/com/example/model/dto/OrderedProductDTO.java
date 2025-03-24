package com.example.model.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderedProductDTO {
    private Long productId;
    private int amount;
    @Nullable
    private Integer price;
}
