package com.example.order.application.command.assembler;

import com.example.order.domain.cart.Cart;
import com.example.order.application.command.output.CartCqrsCommandOutput;
import com.example.shared.application.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class CartCommandAssembler implements CqrsCommandAssembler {
    public CartCqrsCommandOutput toOutput(final Cart cart) {
        return new CartCqrsCommandOutput(cart.getId());
    }
}
