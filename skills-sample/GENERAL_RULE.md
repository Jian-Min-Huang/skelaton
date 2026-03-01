# GENERAL_RULE

## Guidelines

- Clean Architecture, Functional DDD, CQRS 等架構風格的原則和實踐
- 依賴方向由外向內，內層不依賴外層（domain ← application ← presentation / infrastructure）
- 遵守跨層原則，Domain 層的物件不可以直接暴露到 Presentation 層，應該透過 Application 層來做轉換
- 每個 Bounded Context 都有自己的 `package`，每個 `package` 都會有這些 domain, application, presentation, infrastructure `package`
- 預設使用 `final` 修飾子來保護方法和變數不被繼承或修改
- 不要使用 `var` 來宣告變數，應該明確指定變數的型別並使用 `final` 修飾子
- 不要使用基本類型（primitive type），應該使用對應的包裝類（wrapper class）來宣告變數
- 所有日期或時間相關的欄位都應該使用 `java.time.Instant` 類型來表示
- 盡可能使用不可變類（immutable class）來定義資料結構，確保物件的狀態在創建後不會改變
- 遵守單一職責原則（Single Responsibility Principle），確保每個類別或方法只有一個明確的職責
- 遵守最小知識原則（Least Knowledge Principle），確保每個類別或方法只暴露必要的方法和欄位

## Package Naming Conventions

- Enum 的 package 命名為 `enu` 而非 `enum`，因為 `enum` 是 Java 保留字，不可作為 package 名稱

## Package Structure

- `com.example.shared.domain`
- `com.example.shared.application`
- `com.example.shared.presentation`
- `com.example.inventory.domain.product`
- `com.example.inventory.domain.product.entity`
- `com.example.inventory.domain.product.enu`
- `com.example.inventory.domain.product.event`
- `com.example.inventory.domain.product.repository`
- `com.example.inventory.domain.product.vo`
- `com.example.inventory.domain.warehouse`
- `com.example.inventory.domain.warehouse.entity`
- `com.example.inventory.domain.warehouse.enu`
- `com.example.inventory.domain.warehouse.event`
- `com.example.inventory.domain.warehouse.repository`
- `com.example.inventory.domain.warehouse.vo`
- `com.example.inventory.domain.service`
- `com.example.inventory.application`
- `com.example.inventory.application.command`
- `com.example.inventory.application.command.output`
- `com.example.inventory.application.command.assembler`
- `com.example.inventory.application.gateway`
- `com.example.inventory.application.handler`
- `com.example.inventory.application.query`
- `com.example.inventory.application.query.output`
- `com.example.inventory.application.query.assembler`
- `com.example.inventory.presentation.http`
- `com.example.inventory.presentation.http.protocol`
- `com.example.inventory.presentation.http.request`
- `com.example.inventory.presentation.http.request.converter`
- `com.example.inventory.presentation.http.response`
- `com.example.inventory.presentation.http.response.converter`
- `com.example.inventory.infrastructure.adapter`
- `com.example.inventory.infrastructure.configuration`
- `com.example.inventory.infrastructure.persistence`
- `com.example.order.domain.cart`
- `com.example.order.domain.cart.entity`
- `com.example.order.domain.cart.enu`
- `com.example.order.domain.cart.event`
- `com.example.order.domain.cart.repository`
- `com.example.order.domain.cart.vo`
- `com.example.order.domain.order`
- `com.example.order.domain.order.entity`
- `com.example.order.domain.order.enu`
- `com.example.order.domain.order.event`
- `com.example.order.domain.order.repository`
- `com.example.order.domain.order.vo`
- `com.example.order.domain.service`
- `com.example.order.application`
- `com.example.order.application.command`
- `com.example.order.application.command.output`
- `com.example.order.application.command.assembler`
- `com.example.order.application.gateway`
- `com.example.order.application.handler`
- `com.example.order.application.query`
- `com.example.order.application.query.output`
- `com.example.order.application.query.assembler`
- `com.example.order.presentation.http`
- `com.example.order.presentation.http.protocol`
- `com.example.order.presentation.http.request`
- `com.example.order.presentation.http.request.converter`
- `com.example.order.presentation.http.response`
- `com.example.order.presentation.http.response.converter`
- `com.example.order.infrastructure.adapter`
- `com.example.order.infrastructure.configuration`
- `com.example.order.infrastructure.persistence`
