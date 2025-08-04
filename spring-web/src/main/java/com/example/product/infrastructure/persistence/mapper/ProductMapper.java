package com.example.product.infrastructure.persistence.mapper;

import com.example.product.domain.entity.Product;
import com.example.product.domain.vo.Price;
import com.example.product.infrastructure.persistence.po.ProductPo;

import java.time.Instant;

public class ProductMapper {
    public static ProductPo toNewPo(final Product entity) {
        return ProductPo
                .builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(Instant.now())
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(entity.getRemark())
                .deleted(0)
                .version(null)
                .name(entity.getName())
                .description(entity.getDescription())
                .sku(entity.getSku())
                .priceAmount(entity.getPrice().getAmount())
                .priceCurrency(entity.getPrice().getCurrency())
                .category(entity.getCategory())
                .stockQuantity(entity.getStockQuantity())
                .status(entity.getStatus())
                .build();
    }

    public static Product toEntity(final ProductPo po) {
        return Product
                .builder()
                .id(po.getId())
                .createdBy(po.getCreatedBy())
                .lastModifiedBy(po.getLastModifiedBy())
                .deletedBy(po.getDeletedBy())
                .createTime(po.getCreateTime())
                .lastModifyTime(po.getLastModifyTime())
                .deleteTime(po.getDeleteTime())
                .remark(po.getRemark())
                .deleted(po.getDeleted())
                .version(po.getVersion())
                .name(po.getName())
                .description(po.getDescription())
                .sku(po.getSku())
                .price(Price.fromAmountAndCurrency(po.getPriceAmount(), po.getPriceCurrency()))
                .category(po.getCategory())
                .stockQuantity(po.getStockQuantity())
                .status(po.getStatus())
                .build();
    }
}