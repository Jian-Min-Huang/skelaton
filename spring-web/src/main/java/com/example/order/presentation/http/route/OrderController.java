package com.example.order.presentation.http.route;

import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.ExitCode;
import com.example.common.data.Pagination;
import com.example.order.application.port.input.CancelOrderInput;
import com.example.order.application.port.input.CreateOrderInput;
import com.example.order.application.port.input.ModifyOrderStatusInput;
import com.example.order.application.port.input.QueryOrderInput;
import com.example.order.application.port.input.QueryOrdersInput;
import com.example.order.application.port.output.QueryOrderOutputData;
import com.example.order.application.usecase.command.OrderCommandUseCase;
import com.example.order.application.usecase.query.OrderQueryUseCase;
import com.example.order.presentation.http.converter.OrderConverter;
import com.example.order.presentation.http.protocol.OrderProtocol;
import com.example.order.presentation.http.request.CreateOrderRequest;
import com.example.order.presentation.http.request.ModifyOrderStatusRequest;
import com.example.order.presentation.http.request.QueryOrdersRequest;
import com.example.order.presentation.http.response.QueryOrderResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;


@Tag(name = "Order API", description = "APIs for managing orders")
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderProtocol {
    private final OrderCommandUseCase orderCommandUseCase;
    private final OrderQueryUseCase orderQueryUseCase;

    @Override
    public ResponseEntity<Void> createOrder(final CreateOrderRequest request, final HttpServletRequest httpServletRequest) {
        final CreateOrderInput input = OrderConverter.toCreateOrderInput(request);
        final CqrsOutput<?> output = orderCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof Long id) {
            return ResponseEntity
                    .created(URI.create(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/api/v1/orders/" + id))
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<QueryOrderResponse> queryOrderById(final Long id) {
        final QueryOrderInput input = OrderConverter.toQueryOrderInput(id);
        final CqrsOutput<?> output = orderQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof QueryOrderOutputData outputData) {
            final QueryOrderResponse response = OrderConverter.toQueryOrderResponse(outputData);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Pagination<QueryOrderResponse>> queryOrders(final QueryOrdersRequest request) {
        final QueryOrdersInput input = OrderConverter.toQueryOrdersInput(request);
        final CqrsOutput<?> output = orderQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS
                && output.getData() != null
                && output.getData() instanceof Pagination<?> pagination
                && pagination.getContent() != null
                && !pagination.getContent().isEmpty()
                && pagination.getContent().getFirst() instanceof QueryOrderOutputData) {
            final Pagination<QueryOrderResponse> response = Pagination.<QueryOrderResponse>builder()
                    .content(pagination.getContent().stream().map(element -> OrderConverter.toQueryOrderResponse((QueryOrderOutputData) element)).toList())
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
    public ResponseEntity<Void> modifyOrderStatus(final ModifyOrderStatusRequest request) {
        final ModifyOrderStatusInput input = OrderConverter.toModifyOrderStatusInput(request);
        final CqrsOutput<?> output = orderCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> cancelOrderById(final Long id) {
        final CancelOrderInput input = OrderConverter.toCancelOrderInput(id);
        final CqrsOutput<?> output = orderCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }
}