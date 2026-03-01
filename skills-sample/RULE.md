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
- com.example.inventory.usecase
- com.example.inventory.usecase.adapter
- com.example.inventory.usecase.command
- com.example.inventory.usecase.command.output
- com.example.inventory.usecase.command.projector
- com.example.inventory.usecase.handler
- com.example.inventory.usecase.query
- com.example.inventory.usecase.query.output
- com.example.inventory.usecase.query.projector
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
- com.example.order.usecase
- com.example.order.usecase.adapter
- com.example.order.usecase.command
- com.example.order.usecase.command.output
- com.example.order.usecase.command.projector
- com.example.order.usecase.handler
- com.example.order.usecase.query
- com.example.order.usecase.query.output
- com.example.order.usecase.query.projector

## General

- Clean Architecture, Functional DDD, CQRS
- final arguments in methods
- no var, declare with type and final modifier
- no primitive type, use wrapper class instead
- all date or time fields should use java.time.Instant
- layout for single field record class

```java
public record SingleFieldRecordClass(String args) implements SomeInterface {
    // methods
}
```
- layout for multiple fields record class


```java
public record MultipleFieldRecordClass(
        String args1,
        Integer args2,
        BigDecimal args3
) implements SomeInterface {
    // methods
}
```
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

```java
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

- 要用 interface 定義 Domain Repository，並繼承 DomainRepository<T> 介面，其中 T 是 Aggregate Root 的類型

```java
public interface ProductRepository extends DomainRepository<Product> {
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

## Domain Service

```java

```

## CQRS Command

- 使用 record class，並實作 CqrsCommand 介面

```java
public record AddProductVariantCqrsCommand(
        Long productId,
        String variantName,
        String skuCode,
        BigDecimal price,
        Currency currency,
        Integer stockQuantity
) implements CqrsCommand {
}
```

## CQRS Command Output

```java
```

## CQRS Command Projector

```java
```

## CQRS Query

- 使用 record class，並實作 CqrsInput 介面

```java
public record QueryProductByIdCqrsInput(Long productId) implements CqrsQuery {
}
```

## CQRS Query Output

```java
```

## CQRS Query Projector

- 使用 @Component 標注 Projector 類別
- Projector 的職責為轉換 Aggregate Root 為 CqrsOutput

```java
@Component
public class ProductProjector {
    public ProductCqrsOutput toOutput(final Product product) {
        // ignore details
    }
}
```

## Command Use Case

- 要用 @Service, @RequiredArgsConstructor, @Transactional 標注 Command Use Case 類別

```java
```

## Query Use Case

- 要用 @Service, @RequiredArgsConstructor, @Transactional(readOnly = true) 標注 Query Use Case 類別

```java

```

## Domain Gateway

```java

```

## Gateway Adapter

```java

```

## Query Repository

```java

```

## Event Handler

```java

```