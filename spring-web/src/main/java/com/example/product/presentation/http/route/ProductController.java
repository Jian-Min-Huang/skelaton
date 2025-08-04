package com.example.product.presentation.http.route;

import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.ExitCode;
import com.example.common.data.Pagination;
import com.example.product.application.port.input.CreateProductInput;
import com.example.product.application.port.input.ModifyProductPriceInput;
import com.example.product.application.port.input.QueryProductInput;
import com.example.product.application.port.input.QueryProductsInput;
import com.example.product.application.port.input.RemoveProductInput;
import com.example.product.application.port.output.QueryProductOutputData;
import com.example.product.application.usecase.command.ProductCommandUseCase;
import com.example.product.application.usecase.query.ProductQueryUseCase;
import com.example.product.presentation.http.converter.ProductConverter;
import com.example.product.presentation.http.protocol.ProductProtocol;
import com.example.product.presentation.http.request.CreateProductRequest;
import com.example.product.presentation.http.request.ModifyProductPriceRequest;
import com.example.product.presentation.http.request.QueryProductsRequest;
import com.example.product.presentation.http.response.QueryProductResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;


@Tag(name = "Product API", description = "APIs for managing products")
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductProtocol {
    private final ProductCommandUseCase productCommandUseCase;
    private final ProductQueryUseCase productQueryUseCase;

    @Override
    public ResponseEntity<Void> createProduct(final CreateProductRequest request, final HttpServletRequest httpServletRequest) {
        final CreateProductInput input = ProductConverter.toCreateProductInput(request);
        final CqrsOutput<?> output = productCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof Long id) {
            return ResponseEntity
                    .created(URI.create(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/api/v1/products/" + id))
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<QueryProductResponse> queryProductById(final Long id) {
        final QueryProductInput input = ProductConverter.toQueryProductInput(id);
        final CqrsOutput<?> output = productQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof QueryProductOutputData outputData) {
            final QueryProductResponse response = ProductConverter.toQueryProductResponse(outputData);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Pagination<QueryProductResponse>> queryProducts(final QueryProductsRequest request) {
        final QueryProductsInput input = ProductConverter.toQueryProductsInput(request);
        final CqrsOutput<?> output = productQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS
                && output.getData() != null
                && output.getData() instanceof Pagination<?> pagination
                && pagination.getContent() != null
                && !pagination.getContent().isEmpty()
                && pagination.getContent().getFirst() instanceof QueryProductOutputData) {
            final Pagination<QueryProductResponse> response = Pagination.<QueryProductResponse>builder()
                    .content(pagination.getContent().stream().map(element -> ProductConverter.toQueryProductResponse((QueryProductOutputData) element)).toList())
                    .currentPage(pagination.getCurrentPage())
                    .pageSize(pagination.getPageSize())
                    .totalPages(pagination.getTotalPages())
                    .totalElements(pagination.getTotalElements())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(Pagination.empty());
        }
    }

    @Override
    public ResponseEntity<Void> modifyProductPrice(final ModifyProductPriceRequest request) {
        final ModifyProductPriceInput input = ProductConverter.toModifyProductPriceInput(request);
        final CqrsOutput<?> output = productCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> removeProductById(final Long id) {
        final RemoveProductInput input = ProductConverter.toRemoveProductInput(id);
        final CqrsOutput<?> output = productCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }
}