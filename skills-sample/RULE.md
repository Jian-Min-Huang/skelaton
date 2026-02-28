# RULE

## 
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

## General Rules

- Clean Architecture, Functional DDD, CQRS
- final arguments in methods
- no var, declare with type and final modifier
- no primitive type, use wrapper class instead
- Instant

## Aggregate Root Template

- 要用 @Builder, @Value, @With 標注 Aggregate Root，並實作 AggregateRoot 介面
- common fields 包含 id, createdBy, lastModifiedBy, deletedBy, createTime, lastModifyTime, deleteTime, deleted
- custom fields 使用 @Singular 來標記 List, Set, Map 等集合類型的欄位

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
}
```

## Value Object Template

- 要用 record 實作 Value Object，並實作 ValueObject 介面
- 下面展示了單一欄位的排版與多欄位的排版，嚴格遵守此排版方式

```java
public record Sku(String code) implements ValueObject {
}
```

```java
public record Money(
        BigDecimal amount, 
        Currency currency
) implements ValueObject {
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
}
```

## Repository

- Domain Repository 介面只包含這四個基本方法，不能包含任何其他方法

```java
public interface ProductRepository {
    Product save(Product entity);

    Optional<Product> queryById(Long id);

    Integer removeById(Long id);

    Boolean existsById(Long id);
}
```
