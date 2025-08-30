# Infrastructure Layer

## `com.example.member.infrastructure.config`

- 職責：領域相關的 Spring 配置
- 實作原則：
    - 定義 Bean 配置
    - 整合外部服務
    - 例如：`MemberConfiguration`

## `com.example.member.infrastructure.eventbus`

- 職責：事件匯流排實作
- 實作原則：
    - 處理領域事件的發布和訂閱
    - 支援非同步處理
    - 例如：`MemberEventBusImpl`

## `com.example.member.infrastructure.persistence.dao`

- 職責：資料存取物件，定義資料庫操作
- 實作原則：
    - 使用 ORM 框架，如 JPA
    - 例如：`MemberDao`

## `com.example.member.infrastructure.persistence.mapper`

- 職責：物件映射器，負責領域物件與持久化物件的轉換
- 實作原則：
    - 例如：`MemberMapper`

## `com.example.member.infrastructure.persistence.po`

- 職責：持久化物件，對應資料庫表結構
- 實作原則：
    - 使用 JPA 註解定義表對應
    - 例如：`MemberPo`

## `com.example.member.infrastructure.repository`

- 職責：存儲庫介面的實作
- 實作原則：
    - 實作領域層定義的存儲庫介面
    - 例如：
        - `MemberReadonlyRepositoryImpl`
        - `MemberWritableRepositoryImpl`