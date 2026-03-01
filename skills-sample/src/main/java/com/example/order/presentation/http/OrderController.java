package com.example.order.presentation.http;

import com.example.order.application.OrderCommandUseCase;
import com.example.order.application.OrderQueryUseCase;
import com.example.order.application.command.CancelOrderCqrsCommand;
import com.example.order.application.command.ConfirmOrderCqrsCommand;
import com.example.order.application.command.ShipOrderCqrsCommand;
import com.example.order.application.query.QueryOrderByIdCqrsQuery;
import com.example.order.application.query.output.OrderCqrsQueryOutput;
import com.example.order.presentation.http.protocol.OrderProtocol;
import com.example.order.presentation.http.response.OrderResponseDTO;
import com.example.order.presentation.http.response.converter.OrderResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderProtocol {
    private final OrderCommandUseCase commandUseCase;
    private final OrderQueryUseCase queryUseCase;
    private final OrderResponseConverter responseConverter;

    @Override
    public ResponseEntity<OrderResponseDTO> queryOrder(final UUID orderId) {
        final OrderCqrsQueryOutput output = queryUseCase.queryOrderById(new QueryOrderByIdCqrsQuery(orderId));
        return ResponseEntity.ok(responseConverter.toResponse(output));
    }

    @Override
    public ResponseEntity<Void> confirmOrder(final UUID orderId) {
        commandUseCase.confirmOrder(new ConfirmOrderCqrsCommand(orderId));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> shipOrder(final UUID orderId) {
        commandUseCase.shipOrder(new ShipOrderCqrsCommand(orderId));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelOrder(final UUID orderId) {
        commandUseCase.cancelOrder(new CancelOrderCqrsCommand(orderId));
        return ResponseEntity.ok().build();
    }
}
