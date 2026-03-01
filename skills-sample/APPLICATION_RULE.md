# APPLICATION_RULE

## CQRS Command

- 要用 record 實作 CQRS Command，並實作 CqrsCommand 介面來做為標記

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

- 要用 record 實作 CQRS Command Output，並實作 CqrsCommandOutput 介面來做為標記

```java
public record ProductCqrsCommandOutput(Long productId) implements CqrsCommandOutput {
}
```

## CQRS Command Assembler

- 使用 @Component 標注 Command Assembler 類別並實作 CqrsCommandAssembler 介面來做為標記
- Command Assembler 的職責為轉換 Domain Layer 為 CqrsCommandOutput

```java
@Component
public class ProductCommandAssembler implements CqrsCommandAssembler {
    // methods
}
```

## CQRS Query

- 要用 record 實作 CQRS Query，並實作 CqrsQuery 介面來做為標記

```java
public record QueryProductByIdCqrsInput(Long productId) implements CqrsQuery {
}
```

## CQRS Query Output

- 要用 record 實作 CQRS Query Output，並實作 CqrsQueryOutput 介面來做為標記

```java
public record ProductCqrsQueryOutput(
        Long id,
        String name,
        String description,
        String skuCode,
        BigDecimal basePrice,
        Currency currency,
        String brand,
        String model,
        Double weight,
        String weightUnit,
        String dimensions,
        String categoryName,
        String statusName
) implements CqrsQueryOutput {
}
```

## CQRS Query Assembler

- 使用 @Component 標注 Query Assembler 類別並實作 CqrsQueryAssembler 介面來做為標記
- Query Assembler 的職責為轉換 Domain Layer 為 CqrsQueryOutput

```java
@Component
public class ProductQueryAssembler implements CqrsQueryAssembler {
    // methods
}
```

## Command Use Case

- 要用 @Service, @RequiredArgsConstructor, @Transactional 標注 Command Use Case 類別並實作 CqrsCommandUseCase 介面來做為標記

```java
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryCommandUseCase implements CqrsCommandUseCase {
    // fields
    // methods
}
```

## Query Use Case

- 要用 @Service, @RequiredArgsConstructor, @Transactional(readOnly = true) 標注 Query Use Case 類別並實作 CqrsQueryUseCase 介面來做為標記

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryQueryUseCase implements CqrsQueryUseCase {
    // fields
    // methods
}
```

## Domain Gateway

- 要用 interface 定義 Domain Gateway，並繼承 Gateway 介面來做為標記
- Domain Gateway 的職責是定義跨 Bounded Context 的接口，讓不同 BC 之間可以透過 Gateway 進行通信，而不直接依賴對方的實現
- Domain Gateway 的實作會放在 Infrastructure 層，並且由被呼叫方的 BC 提供實作，這樣可以達到 BC 之間的解耦

```java
public interface InventoryGateway extends Gateway {
    // across BC methods
}
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

### 概述

- Event Handler 是 Application 層的元件，負責監聽 Domain Event 並執行應用層面的副作用（side effect）
- Event Handler 不包含商業邏輯，只負責協調（orchestration）：呼叫 Gateway、發送通知、觸發其他 BC 的操作等
- 使用 `@Component` 和 `@RequiredArgsConstructor` 標注，並實作 `EventHandler` 標記介面

### 事件發布流程

Command Use Case 從 `DomainResult` 取出事件，透過 Spring 的 `ApplicationEventPublisher` 發布：

```java
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandUseCase implements CqrsCommandUseCase {
    private final ApplicationEventPublisher eventPublisher;
    // other dependencies...

    public OrderCqrsCommandOutput confirmOrder(final ConfirmOrderCqrsCommand command) {
        final Order order = orderRepository.queryById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));
        final DomainResult<Order> result = order.confirm();
        final Order saved = orderRepository.save(result.entity());
        publishEvents(result);
        return orderCommandAssembler.toOutput(saved);
    }

    private void publishEvents(final DomainResult<?> result) {
        result.events().forEach(eventPublisher::publishEvent);
    }
}
```

### 同 BC 事件處理：@EventListener

- 使用 `@EventListener`，在同一個交易中同步執行
- 適用於同一 Bounded Context 內的副作用，如更新快取、記錄 audit log 等

```java
@Component
@RequiredArgsConstructor
public class ProductActivatedEventHandler implements EventHandler {

    @EventListener
    public void handle(final ProductActivatedEvent event) {
        // 同 BC 內的同步副作用
    }
}
```

### 跨 BC 事件處理：@EventListener + Gateway

- 當事件需要通知另一個 Bounded Context 時，透過 Gateway 介面解耦
- Handler 位於事件發起方的 BC，Gateway 介面也定義在發起方的 BC，Adapter 實作由被呼叫方的 BC 提供

```java
// order BC 的 Handler：監聽 OrderPlacedEvent，透過 Gateway 通知 inventory BC
@Component
@RequiredArgsConstructor
public class OrderPlacedEventHandler implements EventHandler {
    private final InventoryGateway inventoryGateway;

    @EventListener
    public void handle(final OrderPlacedEvent event) {
        // 透過 Gateway 呼叫 inventory BC 預留庫存
        inventoryGateway.allocateStock(productId, quantity);
    }
}
```

### 非同步事件處理：@TransactionalEventListener

- 使用 `@TransactionalEventListener` 在交易提交後非同步執行
- 適用於不需要與主交易保持一致性的副作用，如發送通知、寫入事件日誌等
- 可透過 `phase` 參數控制執行時機

```java
@Component
@RequiredArgsConstructor
public class OrderPlacedNotificationHandler implements EventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(final OrderPlacedEvent event) {
        // 交易提交後才執行：發送通知、寄送 email 等
    }
}
```

### 套件結構

```
com.example.order.application
├── handler/
│   ├── OrderPlacedEventHandler.java           ← 跨 BC：透過 Gateway 通知 inventory
│   └── OrderPlacedNotificationHandler.java    ← 非同步：寄送通知
├── gateway/
│   └── InventoryGateway.java                  ← 跨 BC Gateway 介面

com.example.inventory.application
├── adapter/
│   └── InventoryGatewayAdapter.java           ← Gateway 實作，由被呼叫方的 BC 提供
```

### 命名規則

- Handler 命名格式：`{EventName}Handler` 或 `{EventName}{Purpose}Handler`
- 當同一個事件有多個 Handler 時，用 Purpose 區分，例如：
  - `OrderPlacedEventHandler`（跨 BC 庫存預留）
  - `OrderPlacedNotificationHandler`（發送通知）
