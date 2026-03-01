# DOMAIN_RULE

## Aggregate Root

- 要用 @Builder, @Value, @With 標注 Aggregate Root，並實作 DomainAggregateRoot 介面來做為標記
- Aggregate Root 負責守護其邊界內的商業不變量（Invariant），任何狀態變更都必須通過 Aggregate Root 的方法，且在方法內驗證業務規則後才允許變更
- 不同 Aggregate Root 之間只透過 ID 引用，不持有對方的物件參考
- common fields 包含 id, createdBy, lastModifiedBy, deletedBy, createTime, lastModifyTime, deleteTime, deleted
- custom fields 使用 @Singular 來標記 List, Set, Map 等集合類型的欄位
- 所有方法都要回傳 DomainResult<T>，其中 T 是 Aggregate Root 的類型，並且在 DomainResult 中包含對應的 Domain Event
- 建立 Aggregate Root 使用靜態工廠方法（而非直接暴露 builder），以確保建立時的不變量（初始狀態、必填欄位）和建立事件都由 Domain 層守護

```java
@Builder
@Value
@With
public class Product implements DomainAggregateRoot {
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

- 要用 @Builder, @Value, @With 標注 Entity，並實作 DomainEntity 介面來做為標記
- Entity 的生命週期由 Aggregate Root 管理，不獨立發出 Domain Event
- Entity 的方法回傳修改後的自身實例（透過 @With），由 Aggregate Root 負責將變更包裝成 DomainResult 並附帶 Domain Event

```java
@Builder
@Value
@With
public class ProductVariant implements DomainEntity {
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

- 要用 record 實作 Value Object，並實作 DomainValueObject 介面來做為標記
- Value Object 應在建構時進行自我驗證（Self-Validation），確保建立後的狀態永遠合法

```java
public record Money(
        BigDecimal amount,
        Currency currency
) implements DomainValueObject {
    // constructor with self-validation
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

- Aggregate Root 定義一個 sealed interface 作為 Domain Event 的基底並繼承 DomainEvent，然後使用 permits 關鍵字列出所有的 Domain Event 類別
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

- 保持無框架侵入，所以不要使用 @Service, @Component 等 Spring 標注來標記 Domain Service 類別並實作 DomainService 介面來做為標記
- 這個物件的職責是用來封裝跨越多個 Aggregate Root 的商業邏輯，或者是一些不適合放在 Aggregate Root 裡的方法
- Domain Service 是無狀態的（Stateless），不持有任何可變狀態，所有資料都透過方法參數傳入

```java
public class StockAllocationService implements DomainService {
    // methods
}
```
