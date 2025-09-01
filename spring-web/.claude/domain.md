# Domain Layer

## `com.example.member.domain.entity`

- 職責：定義系統的核心業務實體。這是富含業務邏輯的領域物件，不僅包含狀態(屬性)，也封裝了對應的業務行為(方法)與規則約束
- 實作原則
    - 需要實作 `com.example.common.ddd.domain.DomainEntity<ID>` 介面，`ID` 為實體的唯一標識類型，例如 `Long` 或 `UUID`
    - 實體應負責維護自身的一致性與業務規則
    - 當實體的狀態因業務行為而改變時，應產生相應的領域事件來通知系統其他部分
    - 例如：
        - 當調用 `Member` 物件的 `modifyEmail("foo.bar@example.com")` 方法時
        - 會驗證傳入的 `email` 參數與內部的 `email` 屬性是否不同
        - 若是相同則拋出 `IllegalArgumentException` 異常
        - 若不同則更新內部的 `email` 屬性
        - 並產生一個 `ModifiedMemberEvent` 事件
- 範例檔案：
    - `Member`

## `com.example.member.domain.vo`

- 職責：領域值物件，用於封裝沒有獨立身份、但具有業務意義的概念。它們的相等性由其屬性值決定，且是不可變的物件
- 實作原則：
    - 需要實作 `com.example.common.ddd.domain.ValueObject` 介面
    - 專注於封裝單一業務概念的不可變的物件
- 範例檔案：
    - `PhoneNumber`

## `com.example.member.domain.vo.enu`

- 職責：領域層的列舉定義，作為值物件的一種特殊形式，用於表示一組固定的、有限的選項
- 實作原則：
    - 需要實作 `com.example.common.ddd.domain.ValueObject` 介面
    - 用於表示狀態、類型或分類等業務概念
- 範例檔案：
    - `Gender`
    - `MemberStatus`

## `com.example.member.domain.event`

- 職責：記錄領域模型中已發生的重要業務事實，用於記錄業務實體狀態變更的相關資訊
- 實作原則：
    - 需要實作 `com.example.common.ddd.domain.DomainEvent<T>` 介面
        - `T` 為事件的對應的實體類型，例如 `Member`
    - 使用過去式動詞來命名，命名需要用 `Event` 結尾
    - 事件的命名應清晰地反映其業務含義
    - 事件應攜帶與該業務事實相關的不可變資料
- 範例檔案：
    - `CreatedMemberEvent`
    - `ModifiedMemberEvent`
    - `QueriedMemberEvent`
    - `RemovedMemberEvent`

## `com.example.member.domain.repository.readonly`

- 職責：定義唯讀倉儲介面。此介面遵循命令查詢責任分離原則，專門用於數據查詢操作
- 實作原則：
    - 需要繼承 `com.example.common.ddd.domain.ReadonlyRepository<T, ID>` 介面
        - `T` 為實體類型，例如： `Member`
        - `ID` 為實體的唯一標識類型，例如：`Long` 或 `UUID`
    - 僅包含查詢相關的方法
    - 命名需要用 `ReadonlyRepository` 結尾
- 範例檔案：
    - `MemberReadonlyRepository`

## `com.example.member.domain.repository.writable`

- 職責：定義可寫倉儲介面。此介面遵循命令查詢責任分離原則，專門用於處理實體的創建、更新和刪除等狀態變更操作
- 實作原則：
    - 需要繼承 `com.example.common.ddd.domain.WritableRepository<T, ID>` 介面
        - `T` 為實體類型，例如：`Member`
        - `ID` 為實體的唯一標識類型，例如：`Long` 或 `UUID`
    - 包含創建、更新、刪除等會改變數據狀態的方法
    - 命名需要用 `WritableRepository` 結尾
- 範例檔案：
    - `MemberWritableRepository`
