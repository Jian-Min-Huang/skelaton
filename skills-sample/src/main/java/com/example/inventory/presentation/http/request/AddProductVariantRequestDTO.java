package com.example.inventory.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductVariantRequestDTO implements RequestDTO {
    private UUID productId;
    private String variantName;
    private String skuCode;
    private BigDecimal price;
    private String currency;
    private Integer stockQuantity;
}
