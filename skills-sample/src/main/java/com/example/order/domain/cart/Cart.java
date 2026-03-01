package com.example.order.domain.cart;

import com.example.order.domain.cart.entity.CartItem;
import com.example.order.domain.cart.enu.CartStatus;
import com.example.order.domain.cart.event.CartCheckedOutEvent;
import com.example.order.domain.cart.event.CartCreatedEvent;
import com.example.order.domain.cart.event.CartItemAddedEvent;
import com.example.order.domain.cart.vo.CartDiscount;
import com.example.order.domain.cart.vo.Money;
import com.example.shared.domain.DomainAggregateRoot;
import com.example.shared.domain.DomainResult;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Value
@With
public class Cart implements DomainAggregateRoot {
    // common fields
    Long id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    Long customerId;
    CartStatus status;
    CartDiscount discount;
    @Singular List<CartItem> items;

    public static DomainResult<Cart> create(final Long customerId) {
        final Cart cart = Cart.builder()
                .customerId(customerId)
                .status(CartStatus.ACTIVE)
                .deleted(false)
                .build();
        return DomainResult.of(cart, new CartCreatedEvent(cart.id, customerId, Instant.now()));
    }

    public DomainResult<Cart> addItem(final Long productId,
                                      final String productName,
                                      final Integer quantity,
                                      final Money unitPrice) {
        final CartItem item = CartItem.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .deleted(false)
                .build();
        final List<CartItem> newItems = new ArrayList<>(this.items);
        newItems.add(item);
        final Cart updated = this.withItems(newItems);
        return DomainResult.of(updated,
                new CartItemAddedEvent(this.id, productId, quantity, Instant.now()));
    }

    public DomainResult<Cart> checkout() {
        final Cart checkedOut = this.withStatus(CartStatus.CHECKED_OUT);
        return DomainResult.of(checkedOut, new CartCheckedOutEvent(this.id, Instant.now()));
    }

    public DomainResult<Cart> applyDiscount(final CartDiscount discount) {
        final Cart updated = this.withDiscount(discount);
        return DomainResult.withoutEvents(updated);
    }
}
