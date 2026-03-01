# INFRASTRUCTURE_RULE

## Adapter

- 使用 @Component 和 @Transactional 標注 Gateway Adapter 類別並實作對應的 Domain Gateway 介面

```java
@Component
@Transactional
public class InventoryGatewayAdapter implements InventoryGateway {
    // fields
    // methods
}
```

## Persistence