package com.example.order.domain.order;

import com.example.order.domain.order.entity.OrderItem;
import com.example.order.domain.order.enu.OrderStatus;
import com.example.order.domain.order.enu.PaymentMethod;
import com.example.order.domain.order.event.OrderCancelledEvent;
import com.example.order.domain.order.event.OrderConfirmedEvent;
import com.example.order.domain.order.event.OrderPlacedEvent;
import com.example.order.domain.order.event.OrderShippedEvent;
import com.example.order.domain.order.vo.Money;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final ShippingAddress SHIPPING_ADDRESS =
            new ShippingAddress("John Doe", "0912345678", "Taipei", "Da'an", "Xinyi Rd", "106");
    private static final Money TOTAL_AMOUNT = new Money(BigDecimal.valueOf(199.99), USD);

    private List<OrderItem> createDefaultItems() {
        return List.of(
                OrderItem.builder()
                        .id(UUID.randomUUID())
                        .productId(UUID.randomUUID())
                        .productName("Widget")
                        .quantity(2)
                        .unitPrice(new Money(BigDecimal.valueOf(50), USD))
                        .subtotal(new Money(BigDecimal.valueOf(100), USD))
                        .deleted(false)
                        .build()
        );
    }

    private DomainResult<Order> placeDefaultOrder() {
        return Order.place("ORD-001", CUSTOMER_ID, SHIPPING_ADDRESS, TOTAL_AMOUNT,
                PaymentMethod.CREDIT_CARD, createDefaultItems());
    }

    @Nested
    @DisplayName("place")
    class Place {
        @Test
        @DisplayName("should place order with PENDING status and emit OrderPlacedEvent")
        void shouldPlaceOrder() {
            DomainResult<Order> result = placeDefaultOrder();
            Order order = result.entity();

            assertNotNull(order.getId());
            assertEquals("ORD-001", order.getOrderNumber());
            assertEquals(CUSTOMER_ID, order.getCustomerId());
            assertEquals(SHIPPING_ADDRESS, order.getShippingAddress());
            assertEquals(TOTAL_AMOUNT, order.getTotalAmount());
            assertEquals(OrderStatus.PENDING, order.getStatus());
            assertEquals(PaymentMethod.CREDIT_CARD, order.getPaymentMethod());
            assertFalse(order.getDeleted());
            assertEquals(1, order.getItems().size());

            assertEquals(1, result.events().size());
            assertInstanceOf(OrderPlacedEvent.class, result.events().getFirst());
            OrderPlacedEvent event = (OrderPlacedEvent) result.events().getFirst();
            assertEquals(order.getId(), event.orderId());
            assertEquals(CUSTOMER_ID, event.customerId());
            assertNotNull(event.occurredAt());
        }
    }

    @Nested
    @DisplayName("confirm")
    class Confirm {
        @Test
        @DisplayName("should confirm order from PENDING status and emit OrderConfirmedEvent")
        void shouldConfirmFromPending() {
            Order order = placeDefaultOrder().entity();

            DomainResult<Order> result = order.confirm();
            Order confirmed = result.entity();

            assertEquals(OrderStatus.CONFIRMED, confirmed.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(OrderConfirmedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when confirming from non-PENDING status")
        void shouldThrowWhenNotPending() {
            Order confirmed = placeDefaultOrder().entity().confirm().entity();

            DomainException ex = assertThrows(DomainException.class, confirmed::confirm);
            assertTrue(ex.getMessage().contains("PENDING"));
        }
    }

    @Nested
    @DisplayName("ship")
    class Ship {
        @Test
        @DisplayName("should ship order from CONFIRMED status and emit OrderShippedEvent")
        void shouldShipFromConfirmed() {
            Order confirmed = placeDefaultOrder().entity().confirm().entity();

            DomainResult<Order> result = confirmed.ship();
            Order shipped = result.entity();

            assertEquals(OrderStatus.SHIPPED, shipped.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(OrderShippedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when shipping from non-CONFIRMED status")
        void shouldThrowWhenNotConfirmed() {
            Order pending = placeDefaultOrder().entity();

            DomainException ex = assertThrows(DomainException.class, pending::ship);
            assertTrue(ex.getMessage().contains("CONFIRMED"));
        }
    }

    @Nested
    @DisplayName("cancel")
    class Cancel {
        @Test
        @DisplayName("should cancel order from PENDING status and emit OrderCancelledEvent")
        void shouldCancelFromPending() {
            Order order = placeDefaultOrder().entity();

            DomainResult<Order> result = order.cancel();
            Order cancelled = result.entity();

            assertEquals(OrderStatus.CANCELLED, cancelled.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(OrderCancelledEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should cancel order from CONFIRMED status")
        void shouldCancelFromConfirmed() {
            Order confirmed = placeDefaultOrder().entity().confirm().entity();

            DomainResult<Order> result = confirmed.cancel();

            assertEquals(OrderStatus.CANCELLED, result.entity().getStatus());
        }

        @Test
        @DisplayName("should throw DomainException when cancelling from SHIPPED status")
        void shouldThrowWhenShipped() {
            Order shipped = placeDefaultOrder().entity().confirm().entity().ship().entity();

            DomainException ex = assertThrows(DomainException.class, shipped::cancel);
            assertTrue(ex.getMessage().contains("SHIPPED"));
        }

        @Test
        @DisplayName("should throw DomainException when cancelling from DELIVERED status")
        void shouldThrowWhenDelivered() {
            Order shipped = placeDefaultOrder().entity().confirm().entity().ship().entity();
            Order delivered = shipped.withStatus(OrderStatus.DELIVERED);

            DomainException ex = assertThrows(DomainException.class, delivered::cancel);
            assertTrue(ex.getMessage().contains("DELIVERED"));
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
    @DisplayName("OrderItem entity")
    class OrderItemTest {
        @Test
        @DisplayName("should update quantity and recalculate subtotal")
        void shouldUpdateQuantityAndRecalculate() {
            Money unitPrice = new Money(BigDecimal.valueOf(50), USD);
            OrderItem item = OrderItem.builder()
                    .id(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .productName("Widget")
                    .quantity(2)
                    .unitPrice(unitPrice)
                    .subtotal(new Money(BigDecimal.valueOf(100), USD))
                    .deleted(false)
                    .build();

            OrderItem updated = item.updateQuantity(5);

            assertEquals(5, updated.getQuantity());
            assertEquals(0, BigDecimal.valueOf(250).compareTo(updated.getSubtotal().amount()));
            assertEquals(USD, updated.getSubtotal().currency());
        }
    }
}
