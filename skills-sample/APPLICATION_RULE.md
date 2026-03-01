# APPLICATION_RULE

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

## CQRS Command Assembler

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

## CQRS Query Assembler

- 使用 @Component 標注 Assembler 類別
- Assembler 的職責為轉換 Aggregate Root 為 CqrsOutput

```java
@Component
public class ProductAssembler {
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

## Finder

- 要用 interface 定義 Finder，並繼承 Finder 介面來做為標記
- Finder 的職責是用來查詢資料庫或者其他外部系統的資料，並且轉換成 Domain 層的物件
- 實際的實作會放在 Infrastructure 層
- Finder 與 Domain Repository 是互補的關係，Domain Repository 負責 Aggregate Root 的持久化，而 Finder 負責資料的查詢
- 抱持單一職責原則，Finder 不應該包含任何商業邏輯，只負責資料的查詢和轉換

```java
public interface WarehouseFinder extends Finder {
    List<Warehouse> queryAll();
}
```

## Event Handler

- @EventListener
- @TransactionalEventListener

```java

```
