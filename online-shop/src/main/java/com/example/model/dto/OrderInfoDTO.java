package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderInfoDTO {
    private String address;
    private String orderDescription;
}
