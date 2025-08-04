package com.example.product.application.adapter.projector;

import com.example.common.data.Pagination;
import com.example.product.application.adapter.vo.PriceVoModel;
import com.example.product.application.adapter.vo.enu.CategoryEnuModel;
import com.example.product.application.adapter.vo.enu.ProductStatusEnuModel;
import com.example.product.application.port.input.CreateProductInput;
import com.example.product.application.port.input.ModifyProductPriceInput;
import com.example.product.application.port.output.QueryProductOutputData;
import com.example.product.domain.entity.Product;
import com.example.product.domain.vo.Price;
import com.example.product.domain.vo.enu.Category;
import com.example.product.domain.vo.enu.ProductStatus;

public class ProductProjector {
    public static Product toEntity(final CreateProductInput input) {
        return Product
                .builder()
                .id(null)
                .createdBy(null)
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(null)
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(null)
                .deleted(0)
                .name(input.getName())
                .description(input.getDescription())
                .sku(input.getSku())
                .price(Price.builder().amount(input.getPrice().getAmount()).currency(input.getPrice().getCurrency()).build())
                .category(Category.valueOf(input.getCategory().name()))
                .stockQuantity(input.getStockQuantity())
                .status(ProductStatus.ACTIVE)
                .build();
    }

    public static Product toEntity(final ModifyProductPriceInput input) {
        return Product
                .builder()
                .id(input.getId())
                .price(Price.builder().amount(input.getPrice().getAmount()).currency(input.getPrice().getCurrency()).build())
                .build();
    }

    public static QueryProductOutputData toOutput(final Product entity) {
        return QueryProductOutputData
                .builder()
                .id(entity.getId())
                .createTime(entity.getCreateTime())
                .name(entity.getName())
                .description(entity.getDescription())
                .sku(entity.getSku())
                .price(PriceVoModel.builder().amount(entity.getPrice().getAmount()).currency(entity.getPrice().getCurrency()).build())
                .category(CategoryEnuModel.valueOf(entity.getCategory().name()))
                .stockQuantity(entity.getStockQuantity())
                .status(ProductStatusEnuModel.valueOf(entity.getStatus().name()))
                .build();
    }

    public static Pagination<QueryProductOutputData> toOutput(final Pagination<Product> entities) {
        return Pagination
                .<QueryProductOutputData>builder()
                .content(entities.getContent().stream().map(ProductProjector::toOutput).toList())
                .currentPage(entities.getCurrentPage())
                .pageSize(entities.getPageSize())
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .build();
    }
}