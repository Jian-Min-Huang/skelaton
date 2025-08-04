package com.example.product.presentation.http.request;

import com.example.product.presentation.http.dto.PriceDto;
import com.example.product.presentation.http.dto.enu.CategoryEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @Schema(example = "iPhone 15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;
    @Schema(example = "Latest iPhone with advanced features", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;
    @Schema(example = "IPH-15-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String sku;
    @Valid
    @NotNull
    private PriceDto price;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private CategoryEnuDto category;
    @Schema(example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;
}