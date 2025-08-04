package com.example.product.presentation.http.converter;

import com.example.product.application.adapter.vo.PriceVoModel;
import com.example.product.application.adapter.vo.enu.CategoryEnuModel;
import com.example.product.application.adapter.vo.enu.ProductStatusEnuModel;
import com.example.product.application.port.input.CreateProductInput;
import com.example.product.application.port.input.ModifyProductPriceInput;
import com.example.product.application.port.input.QueryProductInput;
import com.example.product.application.port.input.QueryProductsInput;
import com.example.product.application.port.input.RemoveProductInput;
import com.example.product.application.port.output.QueryProductOutputData;
import com.example.product.presentation.http.dto.PriceDto;
import com.example.product.presentation.http.dto.enu.CategoryEnuDto;
import com.example.product.presentation.http.dto.enu.ProductStatusEnuDto;
import com.example.product.presentation.http.request.CreateProductRequest;
import com.example.product.presentation.http.request.ModifyProductPriceRequest;
import com.example.product.presentation.http.request.QueryProductsRequest;
import com.example.product.presentation.http.response.QueryProductResponse;

public class ProductConverter {
    public static CreateProductInput toCreateProductInput(final CreateProductRequest request) {
        return CreateProductInput
                .builder()
                .id(null)
                .name(request.getName())
                .description(request.getDescription())
                .sku(request.getSku())
                .price(toModel(request.getPrice()))
                .category(toModel(request.getCategory()))
                .stockQuantity(request.getStockQuantity())
                .build();
    }

    public static QueryProductInput toQueryProductInput(final Long id) {
        return QueryProductInput
                .builder()
                .id(id)
                .build();
    }

    public static QueryProductsInput toQueryProductsInput(final QueryProductsRequest request) {
        return QueryProductsInput
                .builder()
                .page(request.getPage())
                .size(request.getSize())
                .name(request.getName())
                .category(request.getCategory())
                .build();
    }

    public static ModifyProductPriceInput toModifyProductPriceInput(final ModifyProductPriceRequest request) {
        return ModifyProductPriceInput
                .builder()
                .id(request.getId())
                .price(toModel(request.getPrice()))
                .build();
    }

    public static RemoveProductInput toRemoveProductInput(final Long id) {
        return RemoveProductInput
                .builder()
                .id(id)
                .build();
    }

    public static QueryProductResponse toQueryProductResponse(final QueryProductOutputData outputData) {
        return QueryProductResponse
                .builder()
                .id(outputData.getId())
                .createTime(outputData.getCreateTime())
                .name(outputData.getName())
                .description(outputData.getDescription())
                .sku(outputData.getSku())
                .price(toDto(outputData.getPrice()))
                .category(toDto(outputData.getCategory()))
                .stockQuantity(outputData.getStockQuantity())
                .status(toDto(outputData.getStatus()))
                .build();
    }

    private static PriceVoModel toModel(final PriceDto dto) {
        if (dto == null) return null;
        return PriceVoModel
                .builder()
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .build();
    }

    private static CategoryEnuModel toModel(final CategoryEnuDto dto) {
        if (dto == null) return null;
        return CategoryEnuModel.valueOf(dto.name());
    }

    private static PriceDto toDto(final PriceVoModel model) {
        if (model == null) return null;
        return PriceDto
                .builder()
                .amount(model.getAmount())
                .currency(model.getCurrency())
                .build();
    }

    private static CategoryEnuDto toDto(final CategoryEnuModel model) {
        if (model == null) return null;
        return CategoryEnuDto.valueOf(model.name());
    }

    private static ProductStatusEnuDto toDto(final ProductStatusEnuModel model) {
        if (model == null) return null;
        return ProductStatusEnuDto.valueOf(model.name());
    }
}