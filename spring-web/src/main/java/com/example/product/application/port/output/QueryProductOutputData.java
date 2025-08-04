package com.example.product.application.port.output;

import com.example.product.application.adapter.vo.PriceVoModel;
import com.example.product.application.adapter.vo.enu.CategoryEnuModel;
import com.example.product.application.adapter.vo.enu.ProductStatusEnuModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryProductOutputData {
    private Long id;
    private Instant createTime;
    private String name;
    private String description;
    private String sku;
    private PriceVoModel price;
    private CategoryEnuModel category;
    private Integer stockQuantity;
    private ProductStatusEnuModel status;
}