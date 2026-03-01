# DOMAIN_RULE

## Aggregate Root

- 用 `@Builder`, `@Value`, `@With` 標注 Aggregate Root，並實作 `DomainAggregateRoot` 介面來做為標記
- Aggregate Root 負責守護其邊界內的商業不變量（Invariant），任何狀態變更都必須通過 Aggregate Root 的方法，且在方法內驗證業務規則後才允許變更
- 不同 Aggregate Root 之間只透過 ID 引用，不持有對方的物件參考
- common fields 包含 `id`, `createdBy`, `updatedBy`, `deletedBy`, `createTime`, `updateTime`, `deleteTime`, `deleted`
- custom fields 使用 `@Singular` 來標記 `List`, `Set`, `Map` 等集合類型的欄位
- 所有方法都要回傳 `DomainResult<T>`，其中 `T` 是 Aggregate Root 的型別，並且在 `DomainResult` 中包含對應的 Domain Event
- 建立 Aggregate Root 使用靜態工廠方法（而非直接暴露 `builder`）以確保建立時的不變量（初始狀態、必填欄位）和建立事件都由 Domain 層守護
- reference code: `src/main/java/com/example/inventory/domain/product/Product.java`

## Entity

- 用 `@Builder`, `@Value`, `@With` 標注 Entity，並實作 `DomainEntity` 介面來做為標記
- common fields 包含 `id`, `createdBy`, `updatedBy`, `deletedBy`, `createTime`, `updateTime`, `deleteTime`, `deleted`
- custom fields 使用 `@Singular` 來標記 `List`, `Set`, `Map` 等集合類型的欄位
- Entity 的生命週期由 Aggregate Root 管理，不獨立發出 Domain Event
- Entity 的方法回傳修改後的自身實例（透過 `@With`）再由 Aggregate Root 負責將變更包裝成 `DomainResult` 並附帶 Domain Event
- reference code: `src/main/java/com/example/inventory/domain/product/entity/ProductVariant.java`

## Value Object

- 用 `record class` 定義 Value Object，並實作 `DomainValueObject` 介面來做為標記
- Value Object 應在建構時進行自我驗證（Self-Validation），確保建立後的狀態永遠合法
- reference code: `src/main/java/com/example/inventory/domain/product/vo/Money.java`

## Enum

- 每一個 Constant 都要換行而且帶上逗號
- 為了避免編譯錯誤，最後一個 Constant 換行之後加上分號
- reference code: `src/main/java/com/example/inventory/domain/product/enu/Category.java`

## Domain Repository

- 要用 `interface` 定義 Domain Repository，並繼承 `DomainRepository<T>` 介面，其中 `T` 是 Aggregate Root 的型別
- Domain Repository 的職責是定義 Aggregate Root 的持久化接口，實際的實作會放在 Infrastructure 層
- reference code: `src/main/java/com/example/inventory/domain/product/repository/ProductRepository.java`

## Domain Event

- Aggregate Root 定義一個 `sealed interface` 作為 Domain Event 的基底並繼承 `DomainEvent`，使用 `permits` 關鍵字列出所有的 Domain Event 類別
- 每個 Domain Event 類別用 `record class` 定義，並實作基底的 `sealed interface`
- reference code: `src/main/java/com/example/inventory/domain/product/event/ProductEvent.java`

## Domain Service

- 保持無框架侵入，所以不要使用 `@Service`, `@Component` 等 Spring 標注來標註 Domain Service 類別
- 實作 `DomainService` 介面來做為標記
- Domain Service 是無狀態的（Stateless），不持有任何可變狀態，所有資料都透過方法參數傳入
- 這個物件的職責是用來封裝同一個 Boundary Context 內多個 Aggregate Root 的商業邏輯，或者是一些不適合放在 Aggregate Root 裡面的方法
- reference code: `src/main/java/com/example/inventory/domain/service/StockAllocationService.java`
