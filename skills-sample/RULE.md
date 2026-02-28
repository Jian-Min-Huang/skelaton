# RULE

## Package Structure

- com.example.inventory.domain.product
  - Aggregate Root: Product
- com.example.inventory.domain.product.entity
- com.example.inventory.domain.product.enu
- com.example.inventory.domain.product.vo
- com.example.inventory.domain.warehouse
  - Aggregate Root: Warehouse
- com.example.inventory.domain.warehouse.entity
- com.example.inventory.domain.warehouse.enu
- com.example.inventory.domain.warehouse.vo
- com.example.inventory.domain.service
- com.example.order.domain.cart
  - Aggregate Root: Cart
- com.example.order.domain.cart.entity
- com.example.order.domain.cart.enu
- com.example.order.domain.cart.vo
- com.example.order.domain.order
  - Aggregate Root: Order
- com.example.order.domain.order.entity
- com.example.order.domain.order.enu
- com.example.order.domain.order.vo
- com.example.order.domain.service

## General

- Clean Architecture, Functional DDD, CQRS
- final arguments in methods
- no var, declare with type and final modifier
- no primitive type, use wrapper class instead
- Instant
- layout for single arguments method

```java
public void singleArgsMethod(final String arg) {
    // method body
}
```

- layout for multiple arguments method, each argument on a new line, aligned with the opening parenthesis of the method declaration, and the closing parenthesis on a new line after the last argument

```java
public void multipleArgsMethod(final String arg1, 
                               final Integer arg2,
                               final BigDecimal arg3) {
  // method body
}
```

## Aggregate Root

- 要用 @Builder, @Value, @With 標注 Aggregate Root，並實作 AggregateRoot 介面
- common fields 包含 id, createdBy, lastModifiedBy, deletedBy, createTime, lastModifyTime, deleteTime, deleted
- custom fields 使用 @Singular 來標記 List, Set, Map 等集合類型的欄位
- 所有方法都要回傳 DomainResult<T>，其中 T 是 Aggregate Root 的類型，並且在 DomainResult 中包含對應的 Domain Event

```java
@Builder
@Value
@With
public class Product implements AggregateRoot {
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
    String name;
    String description;
    Sku sku;
    Money basePrice;
    ProductSpec spec;
    Category category;
    ProductStatus status;
    @Singular List<ProductVariant> variants;
    
    // methods
}
```

## Entity

- 要用 @Builder, @Value, @With 標注 Entity，並實作 Entity 介面
- 其餘規則同 Aggregate Root

```java
@Builder
@Value
@With
public class ProductVariant implements Entity {
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
    String variantName;
    Sku sku;
    Money price;
    Integer stockQuantity;
    
    // methods
}
```

## Value Object

- 要用 record 實作 Value Object，並實作 ValueObject 介面
- 下面展示了單一欄位的排版與多欄位的排版，嚴格遵守此排版方式

```java
public record Sku(String code) implements ValueObject {
    // methods
}

public record Money(
        BigDecimal amount, 
        Currency currency
) implements ValueObject {
    // methods
}
```

## Enum

- 嚴格遵守最後的逗號與分號排版方式

```java
public enum Category {
    ELECTRONICS,
    CLOTHING,
    FOOD,
    FURNITURE,
    BOOKS,
    OTHER,
    ;
    
    // methods
}
```

## Domain Repository

- Domain Repository 介面只包含這四個基本方法，不能包含任何其他方法

```java
public interface ProductRepository {
    Product save(Product entity);
    Optional<Product> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
```

## Domain Event

- Aggregate Root 定義一個 sealed interface 作為 Domain Event 的基底，並且使用 permits 關鍵字列出所有的 Domain Event 類別
- 每個 Domain Event 類別都要用 record 實作，並且實作基底的 sealed interface

```java
public sealed interface ProductEvent extends DomainEvent
        permits ProductCreatedEvent, ProductActivatedEvent, ProductDiscontinuedEvent {
    Long productId();
}

public record ProductCreatedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}

public record ProductActivatedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}

public record ProductDiscontinuedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}
```
