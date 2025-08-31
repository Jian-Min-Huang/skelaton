# Infrastructure Layer

## `com.example.member.infrastructure.config`

- 職責：領域相關的 `Spring Framework` 配置
- 實作原則：
    - 定義 `Spring Framework` 的 `Bean` 配置
- 範例檔案：
    - `MemberConfiguration`

## `com.example.member.infrastructure.eventbus`

- 職責：事件匯流排實作
- 實作原則：
    - 處理領域事件的訂閱與發佈
    - 支援非同步處理
    - 需要實作 `com.example.common.ddd.EventBus`
- 範例檔案：
    - `MemberEventBusImpl`

## `com.example.member.infrastructure.persistence.dao`

- 職責：資料存取物件，定義資料庫操作
- 實作原則：
    - 使用 `ORM` 框架，如 `JPA`
    - 繼承 `org.springframework.data.jpa.repository.JpaRepository<T, ID>` 和 `org.springframework.data.jpa.repository.JpaSpecificationExecutor<T>`
        - `T` 為持久化物件的類別，例如： `MemberPo`
        - `ID` 為實體的唯一標識類型，例如：`Long` 或 `UUID`
- 範例檔案：
    - `MemberDao`

## `com.example.member.infrastructure.persistence.mapper`

- 職責：資料映射器，負責領域物件與持久化物件的轉換
- 實作原則：
    - 責領域層物件會定義在這些 `package` 裡
        - `com.example.member.domain.entity.*`
        - `com.example.member.domain.entity.vo.*`
        - `com.example.member.domain.entity.vo.enu.*`
    - 持久化物件會定義在這些 `package` 裡
        - `com.example.member.infrastructure.persistence.po.*`
- 範例檔案：
    - `MemberMapper`

## `com.example.member.infrastructure.persistence.po`

- 職責：持久化物件，對應資料表結構
- 實作原則：
    - 使用 `ORM` 框架，如 `JPA` 註解來定義資料表的對應
- 範例檔案：
    - `MemberPo`

## `com.example.member.infrastructure.repository`

- 職責：存儲庫介面的實作
- 實作原則：
    - 實作領域層定義的存儲庫介面
  - 需要實作 `MemberReadonlyRepository<T, ID>` 和 `MemberWritableRepository<T, ID>` 介面
      - `T` 為實體類型，例如： `Member`
      - `ID` 為實體的唯一標識類型，例如：`Long` 或 `UUID`
- 範例檔案：
    - `MemberReadonlyRepositoryImpl`
    - `MemberWritableRepositoryImpl`
