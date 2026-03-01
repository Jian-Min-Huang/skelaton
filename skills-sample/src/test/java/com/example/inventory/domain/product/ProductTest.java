package com.example.inventory.domain.product;

import com.example.inventory.domain.product.entity.ProductVariant;
import com.example.inventory.domain.product.enu.Category;
import com.example.inventory.domain.product.enu.ProductStatus;
import com.example.inventory.domain.product.event.ProductActivatedEvent;
import com.example.inventory.domain.product.event.ProductCreatedEvent;
import com.example.inventory.domain.product.event.ProductDiscontinuedEvent;
import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.ProductSpec;
import com.example.inventory.domain.product.vo.Sku;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Sku SKU = new Sku("SKU-001");
    private static final Money BASE_PRICE = new Money(BigDecimal.valueOf(99.99), USD);
    private static final ProductSpec SPEC = new ProductSpec("BrandX", "ModelY", 1.5, "kg", "10x20x30");

    private DomainResult<Product> createDefaultProduct() {
        return Product.create("Test Product", "A test product", SKU, BASE_PRICE, SPEC, Category.ELECTRONICS);
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create product with DRAFT status and emit ProductCreatedEvent")
        void shouldCreateProduct() {
            DomainResult<Product> result = createDefaultProduct();
            Product product = result.entity();

            assertNotNull(product.getId());
            assertEquals("Test Product", product.getName());
            assertEquals("A test product", product.getDescription());
            assertEquals(SKU, product.getSku());
            assertEquals(BASE_PRICE, product.getBasePrice());
            assertEquals(SPEC, product.getSpec());
            assertEquals(Category.ELECTRONICS, product.getCategory());
            assertEquals(ProductStatus.DRAFT, product.getStatus());
            assertFalse(product.getDeleted());
            assertTrue(product.getVariants().isEmpty());

            assertEquals(1, result.events().size());
            assertInstanceOf(ProductCreatedEvent.class, result.events().getFirst());
            ProductCreatedEvent event = (ProductCreatedEvent) result.events().getFirst();
            assertEquals(product.getId(), event.productId());
            assertNotNull(event.occurredAt());
        }
    }

    @Nested
    @DisplayName("activate")
    class Activate {
        @Test
        @DisplayName("should activate product from DRAFT status and emit ProductActivatedEvent")
        void shouldActivateFromDraft() {
            Product product = createDefaultProduct().entity();

            DomainResult<Product> result = product.activate();
            Product activated = result.entity();

            assertEquals(ProductStatus.ACTIVE, activated.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(ProductActivatedEvent.class, result.events().getFirst());
            ProductActivatedEvent event = (ProductActivatedEvent) result.events().getFirst();
            assertEquals(product.getId(), event.productId());
        }

        @Test
        @DisplayName("should throw DomainException when activating from non-DRAFT status")
        void shouldThrowWhenNotDraft() {
            Product active = createDefaultProduct().entity().activate().entity();

            DomainException ex = assertThrows(DomainException.class, active::activate);
            assertTrue(ex.getMessage().contains("DRAFT"));
        }
    }

    @Nested
    @DisplayName("discontinue")
    class Discontinue {
        @Test
        @DisplayName("should discontinue product from ACTIVE status and emit ProductDiscontinuedEvent")
        void shouldDiscontinueFromActive() {
            Product active = createDefaultProduct().entity().activate().entity();

            DomainResult<Product> result = active.discontinue();
            Product discontinued = result.entity();

            assertEquals(ProductStatus.DISCONTINUED, discontinued.getStatus());
            assertEquals(1, result.events().size());
            assertInstanceOf(ProductDiscontinuedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when discontinuing from non-ACTIVE status")
        void shouldThrowWhenNotActive() {
            Product draft = createDefaultProduct().entity();

            DomainException ex = assertThrows(DomainException.class, draft::discontinue);
            assertTrue(ex.getMessage().contains("ACTIVE"));
        }
    }

    @Nested
    @DisplayName("addVariant")
    class AddVariant {
        @Test
        @DisplayName("should add variant to product without events")
        void shouldAddVariant() {
            Product product = createDefaultProduct().entity();
            ProductVariant variant = ProductVariant.builder()
                    .id(UUID.randomUUID())
                    .variantName("Red - Large")
                    .sku(new Sku("SKU-001-RL"))
                    .price(new Money(BigDecimal.valueOf(109.99), USD))
                    .stockQuantity(50)
                    .deleted(false)
                    .build();

            DomainResult<Product> result = product.addVariant(variant);

            assertEquals(1, result.entity().getVariants().size());
            assertEquals(variant, result.entity().getVariants().getFirst());
            assertTrue(result.events().isEmpty());
        }
    }

    @Nested
    @DisplayName("removeVariant")
    class RemoveVariant {
        @Test
        @DisplayName("should remove variant by id without events")
        void shouldRemoveVariant() {
            Product product = createDefaultProduct().entity();
            UUID variantId = UUID.randomUUID();
            ProductVariant variant = ProductVariant.builder()
                    .id(variantId)
                    .variantName("Red - Large")
                    .sku(new Sku("SKU-001-RL"))
                    .price(new Money(BigDecimal.valueOf(109.99), USD))
                    .stockQuantity(50)
                    .deleted(false)
                    .build();
            product = product.addVariant(variant).entity();

            DomainResult<Product> result = product.removeVariant(variantId);

            assertTrue(result.entity().getVariants().isEmpty());
            assertTrue(result.events().isEmpty());
        }

        @Test
        @DisplayName("should do nothing when variant id not found")
        void shouldDoNothingWhenNotFound() {
            Product product = createDefaultProduct().entity();
            UUID variantId = UUID.randomUUID();
            ProductVariant variant = ProductVariant.builder()
                    .id(variantId)
                    .variantName("Red - Large")
                    .sku(new Sku("SKU-001-RL"))
                    .price(new Money(BigDecimal.valueOf(109.99), USD))
                    .stockQuantity(50)
                    .deleted(false)
                    .build();
            product = product.addVariant(variant).entity();

            DomainResult<Product> result = product.removeVariant(UUID.randomUUID());

            assertEquals(1, result.entity().getVariants().size());
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
    @DisplayName("ProductVariant entity")
    class ProductVariantTest {
        @Test
        @DisplayName("should update price")
        void shouldUpdatePrice() {
            ProductVariant variant = ProductVariant.builder()
                    .id(UUID.randomUUID())
                    .variantName("Blue")
                    .sku(new Sku("SKU-BLU"))
                    .price(new Money(BigDecimal.TEN, USD))
                    .stockQuantity(10)
                    .deleted(false)
                    .build();

            Money newPrice = new Money(BigDecimal.valueOf(20), USD);
            ProductVariant updated = variant.updatePrice(newPrice);

            assertEquals(newPrice, updated.getPrice());
            assertEquals(variant.getVariantName(), updated.getVariantName());
        }

        @Test
        @DisplayName("should update stock quantity")
        void shouldUpdateStockQuantity() {
            ProductVariant variant = ProductVariant.builder()
                    .id(UUID.randomUUID())
                    .variantName("Blue")
                    .sku(new Sku("SKU-BLU"))
                    .price(new Money(BigDecimal.TEN, USD))
                    .stockQuantity(10)
                    .deleted(false)
                    .build();

            ProductVariant updated = variant.updateStockQuantity(25);

            assertEquals(25, updated.getStockQuantity());
        }
    }
}
