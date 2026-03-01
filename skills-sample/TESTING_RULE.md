# TESTING_RULE

## Testing Strategy

- 採用兩層測試互補策略：**Domain 單元測試**快速守護商業邏輯，**整合測試**確保各層接線正確
- Domain 層必須撰寫單元測試，Application / Presentation / Infrastructure 層由整合測試覆蓋，不需額外撰寫單元測試

| 層 | 測試類型 | 說明 |
|----|---------|------|
| Domain | 單元測試 | 必寫，守護商業規則 |
| Application | 整合測試覆蓋 | 編排邏輯（load → call domain → save → publish events）由整合測試驗證 |
| Presentation | 整合測試覆蓋 | HTTP 綁定（路由、參數解析、回應格式）由整合測試打 API 驗證 |
| Infrastructure | 整合測試覆蓋 | Repository 實作、Adapter 實作由整合測試搭配測試資料庫驗證 |

## Domain Unit Test

- Domain 層的測試為純單元測試，不依賴任何框架、不需要 mock，直接對 Domain 物件進行驗證
- 測試類別命名為 `{ClassName}Test`，放在對應的 test package 中

### Aggregate Root Test

- 驗證靜態工廠方法建立的初始狀態與預期一致（包含 `id` 不為 null、`status` 為初始值、`deleted` 為 false）
- 驗證靜態工廠方法回傳的 `DomainResult` 包含正確的 Domain Event
- 驗證狀態轉換的守護邏輯：合法的前置狀態可以轉換，不合法的前置狀態拋出 `DomainException`
- 驗證集合操作（如新增、移除子 Entity）後的集合內容正確
- 每個 Aggregate Root 方法至少要有一個成功案例和一個失敗案例（若有守護邏輯）

### Value Object Test

- 驗證 Self-Validation：非法值（null、負數等）在建構時拋出例外
- 驗證行為方法的計算邏輯正確（如 `StockLevel.reserve()` 的庫存扣減）
- 驗證行為方法的守護邏輯（如 `StockLevel.reserve()` 超量時拋出 `DomainException`）

### Domain Service Test

- 驗證跨 Aggregate Root 的協調邏輯是否正確
- 驗證業務規則違反時拋出 `DomainException`
- Domain Service 是無狀態的，測試時直接 new 實例即可，不需要 mock

## Integration Test

- 整合測試搭配測試資料庫與 API 呼叫，驗證從 Presentation 層到 Infrastructure 層的端到端流程
- 整合測試覆蓋 Application 層的編排邏輯、Assembler 的欄位映射、Repository 的持久化、以及 Event 的發布與處理
