package com.example.order.usecase.command.projector;

import com.example.order.domain.cart.Cart;
import com.example.order.usecase.command.output.CartCqrsCommandOutput;
import com.example.shared.cqrs.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class CartCommandAssembler implements CqrsCommandAssembler {
    public CartCqrsCommandOutput toOutput(final Cart cart) {
        return new CartCqrsCommandOutput(cart.getId());
    }
}
