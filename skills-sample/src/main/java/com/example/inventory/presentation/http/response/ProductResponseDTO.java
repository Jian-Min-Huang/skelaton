package com.example.inventory.presentation.http.response;

import com.example.shared.presentation.http.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO implements ResponseDTO {
    private UUID id;
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
    private String status;
}
