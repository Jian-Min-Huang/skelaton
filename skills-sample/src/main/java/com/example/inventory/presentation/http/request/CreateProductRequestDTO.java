package com.example.inventory.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDTO implements RequestDTO {
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal basePrice;
    private String currency;
    private String brand;
    private String model;
    private Double weight;
    private String weightUnit;
    private String dimensions;
    private String category;
}
