package com.example.order.domain.cart;

import com.example.order.domain.cart.entity.CartItem;
import com.example.order.domain.cart.enu.CartStatus;
import com.example.order.domain.cart.event.CartCheckedOutEvent;
import com.example.order.domain.cart.event.CartCreatedEvent;
import com.example.order.domain.cart.event.CartItemAddedEvent;
import com.example.order.domain.cart.vo.CartDiscount;
import com.example.order.domain.cart.vo.Money;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final UUID CUSTOMER_ID = UUID.randomUUID();

    private DomainResult<Cart> createDefaultCart() {
        return Cart.create(CUSTOMER_ID);
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create cart with ACTIVE status and emit CartCreatedEvent")
        void shouldCreateCart() {
            DomainResult<Cart> result = createDefaultCart();
            Cart cart = result.entity();

            assertNotNull(cart.getId());
            assertEquals(CUSTOMER_ID, cart.getCustomerId());
            assertEquals(CartStatus.ACTIVE, cart.getStatus());
            assertFalse(cart.getDeleted());
            assertTrue(cart.getItems().isEmpty());

            assertEquals(1, result.events().size());
            assertInstanceOf(CartCreatedEvent.class, result.events().getFirst());
            CartCreatedEvent event = (CartCreatedEvent) result.events().getFirst();
            assertEquals(cart.getId(), event.cartId());
            assertEquals(CUSTOMER_ID, event.customerId());
            assertNotNull(event.occurredAt());
        }
    }

    @Nested
    @DisplayName("addItem")
    class AddItem {
        @Test
        @DisplayName("should add item to active cart and emit CartItemAddedEvent")
        void shouldAddItemToActiveCart() {
            Cart cart = createDefaultCart().entity();
            UUID productId = UUID.randomUUID();
            Money unitPrice = new Money(BigDecimal.valueOf(29.99), USD);

            DomainResult<Cart> result = cart.addItem(productId, "Widget", 2, unitPrice);
            Cart updated = result.entity();

            assertEquals(1, updated.getItems().size());
            CartItem item = updated.getItems().getFirst();
            assertEquals(productId, item.getProductId());
            assertEquals("Widget", item.getProductName());
            assertEquals(2, item.getQuantity());
            assertEquals(unitPrice, item.getUnitPrice());
            assertFalse(item.getDeleted());

            assertEquals(1, result.events().size());
            assertInstanceOf(CartItemAddedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should add multiple items")
        void shouldAddMultipleItems() {
            Cart cart = createDefaultCart().entity();
            Money price1 = new Money(BigDecimal.valueOf(10), USD);
            Money price2 = new Money(BigDecimal.valueOf(20), USD);

            cart = cart.addItem(UUID.randomUUID(), "Item1", 1, price1).entity();
            cart = cart.addItem(UUID.randomUUID(), "Item2", 3, price2).entity();

            assertEquals(2, cart.getItems().size());
        }

        @Test
        @DisplayName("should throw DomainException when adding item to non-ACTIVE cart")
        void shouldThrowWhenNotActive() {
            Cart cart = createDefaultCart().entity().checkout().entity();
            Money unitPrice = new Money(BigDecimal.valueOf(10), USD);

            DomainException ex = assertThrows(DomainException.class,
                    () -> cart.addItem(UUID.randomUUID(), "Widget", 1, unitPrice));
            assertTrue(ex.getMessage().contains("ACTIVE"));
        }
    }

    @Nested
    @DisplayName("checkout")
    class Checkout {
        @Test
        @DisplayName("should checkout active cart and emit CartCheckedOutEvent")
        void shouldCheckoutActiveCart() {
            Cart cart = createDefaultCart().entity();

            DomainResult<Cart> result = cart.checkout();
            Cart checkedOut = result.entity();

            assertEquals(CartStatus.CHECKED_OUT, checkedOut.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(CartCheckedOutEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when checking out non-ACTIVE cart")
        void shouldThrowWhenNotActive() {
            Cart cart = createDefaultCart().entity().checkout().entity();

            DomainException ex = assertThrows(DomainException.class, cart::checkout);
            assertTrue(ex.getMessage().contains("ACTIVE"));
        }
    }

    @Nested
    @DisplayName("applyDiscount")
    class ApplyDiscount {
        @Test
        @DisplayName("should apply discount without events")
        void shouldApplyDiscount() {
            Cart cart = createDefaultCart().entity();
            CartDiscount discount = new CartDiscount("SAVE10", new Money(BigDecimal.TEN, USD));

            DomainResult<Cart> result = cart.applyDiscount(discount);

            assertEquals(discount, result.entity().getDiscount());
            assertTrue(result.events().isEmpty());
        }
    }

    @Nested
    @DisplayName("Money value object")
    class MoneyTest {
        @Test
        @DisplayName("should reject null amount")
        void shouldRejectNullAmount() {
            assertThrows(NullPointerException.class, () -> new Money(null, USD));
        }

        @Test
        @DisplayName("should reject null currency")
        void shouldRejectNullCurrency() {
            assertThrows(NullPointerException.class, () -> new Money(BigDecimal.ONE, null));
        }

        @Test
        @DisplayName("should reject negative amount")
        void shouldRejectNegativeAmount() {
            assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.valueOf(-1), USD));
        }
    }

    @Nested
    @DisplayName("CartItem entity")
    class CartItemTest {
        @Test
        @DisplayName("should update quantity")
        void shouldUpdateQuantity() {
            CartItem item = CartItem.builder()
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .productName("Widget")
                    .quantity(2)
                    .unitPrice(new Money(BigDecimal.TEN, USD))
                    .deleted(false)
                    .build();

            CartItem updated = item.updateQuantity(5);

            assertEquals(5, updated.getQuantity());
            assertEquals(item.getProductName(), updated.getProductName());
        }
    }
}
