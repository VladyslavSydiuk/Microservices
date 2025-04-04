package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderedProductDTO {
    private Long productId;
    private int amount;
}
