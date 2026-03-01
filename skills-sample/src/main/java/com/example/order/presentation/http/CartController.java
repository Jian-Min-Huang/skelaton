package com.example.order.presentation.http;

import com.example.order.application.OrderCommandUseCase;
import com.example.order.application.OrderQueryUseCase;
import com.example.order.application.command.output.CartCqrsCommandOutput;
import com.example.order.application.command.output.OrderCqrsCommandOutput;
import com.example.order.application.query.QueryCartByIdCqrsQuery;
import com.example.order.application.query.output.CartCqrsQueryOutput;
import com.example.order.presentation.http.protocol.CartProtocol;
import com.example.order.presentation.http.request.AddCartItemRequestDTO;
import com.example.order.presentation.http.request.CheckoutRequestDTO;
import com.example.order.presentation.http.request.CreateCartRequestDTO;
import com.example.order.presentation.http.request.converter.CartRequestConverter;
import com.example.order.presentation.http.response.CartResponseDTO;
import com.example.order.presentation.http.response.converter.CartResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartController implements CartProtocol {
    private final OrderCommandUseCase commandUseCase;
    private final OrderQueryUseCase queryUseCase;
    private final CartRequestConverter requestConverter;
    private final CartResponseConverter responseConverter;

    @Override
    public ResponseEntity<Void> createCart(final CreateCartRequestDTO request) {
        final CartCqrsCommandOutput output = commandUseCase.createCart(requestConverter.toCommand(request));
        return ResponseEntity.created(URI.create("/v1/carts/" + output.cartId())).build();
    }

    @Override
    public ResponseEntity<CartResponseDTO> queryCart(final UUID cartId) {
        final CartCqrsQueryOutput output = queryUseCase.queryCartById(new QueryCartByIdCqrsQuery(cartId));
        return ResponseEntity.ok(responseConverter.toResponse(output));
    }

    @Override
    public ResponseEntity<Void> addCartItem(final UUID cartId, final AddCartItemRequestDTO request) {
        commandUseCase.addCartItem(requestConverter.toCommand(cartId, request));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> checkout(final UUID cartId, final CheckoutRequestDTO request) {
        final OrderCqrsCommandOutput output = commandUseCase.checkout(requestConverter.toCommand(cartId, request));
        return ResponseEntity.created(URI.create("/v1/orders/" + output.orderId())).build();
    }
}
