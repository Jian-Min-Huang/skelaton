# APPLICATION_RULE

## CQRS Command

- 用 `record class` 定義 CQRS Command，並實作 `CqrsCommand` 介面來做為標記
- 每個 Command 代表一個業務意圖（Business Intention），命名使用動詞開頭對應一個業務操作
- 當一個業務意圖天然涉及多個 Aggregate Root 時（如結帳同時更新購物車與建立訂單），Command 可攜帶所有必要資料，由 Domain Service 負責協調；不應為了形式上的拆分而製造不一致的中間狀態
- reference code: `src/main/java/com/example/inventory/application/command/ActivateProductCqrsCommand.java`

## CQRS Command Output

- 用 `record class` 定義 CQRS Command Output，並實作 `CqrsCommandOutput` 介面來做為標記
- Command Output 的職責是封裝 Command 執行後的結果資料
- reference code: `src/main/java/com/example/inventory/application/command/output/ProductCqrsCommandOutput.java`

## CQRS Command Assembler

- 用 `@Component` 標注 Command Assembler 類別並實作 `CqrsCommandAssembler` 介面來做為標記
- Command Assembler 的職責為轉換 Domain Layer 為 CQRS Command Output
- Command → Value Object 的組裝則屬於 Command Use Case 的協調職責，不由 Assembler 負責
- reference code: `src/main/java/com/example/inventory/application/command/assembler/ProductCommandAssembler.java`

## CQRS Query

- 用 `record class` 定義 CQRS Query，並實作 `CqrsQuery` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/application/query/QueryProductByIdCqrsQuery.java`

## CQRS Query Output

- 用 `record class` 定義 CQRS Query Output，並實作 `CqrsQueryOutput` 介面來做為標記
- Query Output 的職責是封裝 Query 執行後的結果資料
- reference code: `src/main/java/com/example/inventory/application/query/output/ProductCqrsQueryOutput.java`

## CQRS Query Assembler

- 用 `@Component` 標注 Query Assembler 類別並實作 CqrsQueryAssembler 介面來做為標記
- Query Assembler 的職責為轉換 Domain Layer 為 CQRS Query Output
- reference code: `src/main/java/com/example/inventory/application/query/assembler/ProductQueryAssembler.java`

## Command Use Case

- 用 `@Service`, `@RequiredArgsConstructor`, `@Transactional` 標注 Command Use Case 類別並實作 `CqrsCommandUseCase` 介面來做為標記
- 標準操作流程為： receive command → assemble VO → load/create aggregate → call domain method → save → publish events → return output
- 當需要透過 Domain Service 協調多個 Aggregate Root 時，操作流程調整為： receive command → load aggregates → call domain service → save affected aggregates → publish all events → return output
- reference code: `src/main/java/com/example/inventory/application/InventoryCommandUseCase.java`

## Query Use Case

- 用 `@Service`, `@RequiredArgsConstructor`, `@Transactional(readOnly = true)` 標注 Query Use Case 類別並實作 `CqrsQueryUseCase` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/application/InventoryQueryUseCase.java`

## Gateway

- 用 `interface` 定義 Gateway，並繼承 `Gateway` 介面來做為標記
- Gateway 的職責是定義跨 Bounded Context 的接口，讓不同 Bounded Context 之間可以透過 Gateway 進行通信，而不直接依賴對方的實作
- Gateway 介面定義在呼叫方的 Application 層（如 `order.application.gateway`），Adapter 實作放在呼叫方的 Infrastructure 層（如 `order.infrastructure.adapter`），形成完整的 Anti-Corruption Layer
- Adapter 內部直接呼叫被呼叫方的 Application 層（如 `inventory.application.InventoryCommandUseCase`），確保依賴方向為單向：呼叫方 → 被呼叫方，被呼叫方不需知道呼叫方的存在
- 未來拆分微服務時，只需將 Adapter 的實作從直接呼叫改為 HTTP/gRPC client，Gateway 介面與呼叫方程式碼不需變動
- reference code: `src/main/java/com/example/order/application/gateway/InventoryGateway.java`

## Finder

- 用 `interface` 定義 Finder，並繼承 `Finder` 介面來做為標記
- Finder 的職責是用來查詢資料庫或者其他外部系統的資料，並且轉換成 Domain 層的物件
- Finder 的實作會放在自己這個 Bounded Context 的 Infrastructure 層
- Finder 與 Domain Repository 是互補的關係，Domain Repository 負責 Aggregate Root 的持久化，而 Finder 負責資料查詢
- Finder 不應該包含任何商業邏輯，只負責資料的查詢和轉換
- reference code: `src/main/java/com/example/inventory/application/WarehouseFinder.java`

## Event Handler

- 用 `@Component`, `@RequiredArgsConstructor` 標注 Event Handler 類別並實作 `EventHandler` 介面來做為標記

### 相同 Bounded Context 的事件處理: @EventListener

- 使用 `@EventListener`，在同一個交易中同步執行
- 適用於同一 Bounded Context 內的副作用，例如更新快取或是記錄稽核日誌

### 相同 Bounded Context 的非同步事件處理: @TransactionalEventListener

- 使用 `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` 在交易提交後非同步執行
- 適用於同一 Bounded Context 內不需要與主交易保持一致性的副作用，例如發送通知或寫入事件日誌等
- 可透過 `phase` 參數控制執行時機
- 使用 `@TransactionalEventListener` 如果需要寫入資料庫，要在 Handler 上加上 `@Transactional(propagation = Propagation.REQUIRES_NEW)`，因為事件處理是在主交易提交後才執行的
