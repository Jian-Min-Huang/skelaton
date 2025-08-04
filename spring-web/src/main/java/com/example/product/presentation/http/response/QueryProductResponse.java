package com.example.product.presentation.http.response;

import com.example.product.presentation.http.dto.PriceDto;
import com.example.product.presentation.http.dto.enu.CategoryEnuDto;
import com.example.product.presentation.http.dto.enu.ProductStatusEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryProductResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "2024-01-01T00:00:00Z")
    private Instant createTime;
    @Schema(example = "iPhone 15")
    private String name;
    @Schema(example = "Latest iPhone with advanced features")
    private String description;
    @Schema(example = "IPH-15-001")
    private String sku;
    private PriceDto price;
    private CategoryEnuDto category;
    @Schema(example = "100")
    private Integer stockQuantity;
    private ProductStatusEnuDto status;
}