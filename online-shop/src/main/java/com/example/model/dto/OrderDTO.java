package com.example.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private List<OrderedProductDTO> orderedProducts;
}
