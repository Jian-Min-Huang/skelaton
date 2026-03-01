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

## Configuration

- 使用 @Configuration 類別來註冊 Domain 層不帶 Spring 標注的物件（如 Domain Service）為 Spring Bean
- 每個 Bounded Context 一個 Configuration 類別

```java
@Configuration
public class InventoryConfiguration {
    @Bean
    public StockAllocationService stockAllocationService() {
        return new StockAllocationService();
    }
}
```

## Persistence