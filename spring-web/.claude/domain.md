# Domain Layer

## `com.example.member.domain.entity`

- 職責：核心業務實體，包含業務邏輯、狀態、規則約束
- 實作原則：
    - 富含業務邏輯的領域物件
    - 維護業務規則約束
    - 發布領域事件
    - 例如：`Member`

## `com.example.member.domain.event`

- 職責：領域事件定義，記錄重要的業務狀態變化
- 實作原則：
    - 事件名稱應反映業務含義
    - 包含事件發生時的相關資料
    - 例如：
        - `CreateMemberEvent`
        - `ModifyMemberEvent`
        - `QueryMemberEvent`
        - `RemoveMemberEvent`

## `com.example.member.domain.repository.readonly`

- 職責：唯讀存儲庫介面，用於查詢操作
- 實作原則：
    - 只提供查詢方法
    - 例如：`MemberReadonlyRepository`

## `com.example.member.domain.repository.writable`

- 職責：可寫存儲庫介面，用於狀態修改操作
- 實作原則：
    - 提供創建、更新、刪除方法
    - 例如：`MemberWritableRepository`

## `com.example.member.domain.vo.enu`

- 職責：領域層的列舉定義
    - 例如：
        - `Gender`
        - `MemberStatus`

## `com.example.member.domain.vo`

- 職責：領域值物件，封裝業務概念
    - 例如：`PhoneNumber`
