package com.example.product.infrastructure.persistence.po;

import com.example.product.domain.vo.enu.Category;
import com.example.product.domain.vo.enu.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class ProductPo {
    // common fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String createdBy;
    private String lastModifiedBy;
    private String deletedBy;
    private Instant createTime;
    private Instant lastModifyTime;
    private Instant deleteTime;
    private String remark;
    private Integer deleted;
    private Integer version;

    // other fields
    private String name;
    private String description;
    private String sku;
    private BigDecimal priceAmount;
    private String priceCurrency;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    private Integer stockQuantity;
    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;
}