package com.example.order.application.query.assembler;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.cart.vo.CartDiscount;
import com.example.order.application.query.output.CartCqrsQueryOutput;
import com.example.shared.application.CqrsQueryAssembler;
import org.springframework.stereotype.Component;

@Component
public class CartQueryAssembler implements CqrsQueryAssembler {
    public CartCqrsQueryOutput toOutput(final Cart cart) {
        final CartDiscount discount = cart.getDiscount();
        return new CartCqrsQueryOutput(
                cart.getId(),
                cart.getCustomerId(),
                cart.getStatus().name(),
                discount != null ? discount.couponCode() : null,
                discount != null ? discount.discountAmount().amount() : null,
                discount != null ? discount.discountAmount().currency() : null
        );
    }
}
