# Application Layer

## `com.example.member.application.adapter.projector`

- 職責：資料投影器，負責領域物件與應用層資料物件的轉換
- 實作原則：
    - 例如：`MemberProjector`

## `com.example.member.application.adapter.vo`

- 職責：應用層使用的值物件模型，用於應用層的資料傳遞
- 實作原則：
    - 封裝領域層值物件，避免直接暴露領域概念到外層
    - 例如：`PhoneNumberVoModel`

## `com.example.member.application.adapter.vo.enu`

- 職責：應用層使用的列舉模型，用於應用層的資料傳遞
- 實作原則：
    - 封裝領域層列舉，避免直接暴露領域概念到外層
  - 例如：
      - `GenderEnuModel`
      - `MemberStatusEnuModel`

## `com.example.member.application.port.input`

- 職責：定義輸入埠的資料傳輸物件，用於接收外部請求
- 實作原則：
    - 所有類別命名必須以 `InputData` 結尾
    - 不包含業務邏輯，純粹的資料容器
  - 需要實作 `com.example.common.ddd.cqrs.CqrsInput<ID>` 介面
  - 例如：
      - `CreateMemberInputData`
      - `ModifyMemberEmailInputData`
      - `QueryMemberInputData`
      - `QueryMembersInputData`
      - `RemoveMemberInputData`

## `com.example.member.application.port.output`

- 職責：定義輸出埠的資料傳輸物件，用於返回處理結果
- 實作原則：
    - 所有類別命名必須以 `OutputData` 結尾
    - 封裝查詢結果和處理狀態
    - 例如：`QueryMemberOutputData`

## `com.example.member.application.usecase.command`

- 職責：處理命令操作的用例實作
- 實作原則：
    - 實作 `CQRS` 的 `Command` 端
    - 處理創建、修改、刪除等會改變狀態的操作
    - 發布對應的領域事件
    - 需要實作 `com.example.common.ddd.cqrs.CqrsTemplate` 介面
    - 例如：`MemberCommandUseCase`

## `com.example.member.application.usecase.query`

- 職責：處理查詢操作的用例實作
- 實作原則：
    - 實作 `CQRS` 的 `Query` 端
    - 只處理資料查詢，不改變狀態
    - 發布對應的領域事件
    - 需要實作 `com.example.common.ddd.cqrs.CqrsTemplate` 介面
    - 例如：`MemberQueryUseCase`
