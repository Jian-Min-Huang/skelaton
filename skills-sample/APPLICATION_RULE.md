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