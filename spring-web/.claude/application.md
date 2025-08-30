# Application Layer (應用層)

## `com.example.member.application.adapter.projector`

- 職責：處理領域事件的投影，將事件轉換為查詢模型
- 實作原則：

- 監聽領域事件，更新讀取模型
- 實作事件驅動的 CQRS 讀寫分離
- 例如：`MemberProjector` 監聽會員相關事件，更新會員查詢視圖

## `com.example.member.application.adapter.vo.enu`

- 職責：應用層使用的列舉值模型，用於應用服務間的資料傳遞
- 實作原則：
    - 封裝領域層列舉，避免直接暴露領域概念到外層
    - 提供應用層專用的列舉轉換邏輯
    - 例如：`GenderEnuModel`、`MemberStatusEnuModel`

## `com.example.member.application.adapter.vo`

- 職責：應用層的值物件，封裝複雜的業務概念
- 實作原則：
    - 將領域值物件轉換為應用層使用的格式
    - 提供驗證邏輯和格式化功能
    - 例如：`PhoneNumberVoModel` 處理電話號碼的格式化和驗證

## `com.example.member.application.port.input`

- 職責：定義輸入埠的資料傳輸物件，用於接收外部請求
- 實作原則：
    - 所有類別命名必須以 `InputData` 結尾
    - 包含必要的驗證註解
    - 不包含業務邏輯，純粹的資料容器
    - 例如：`CreateMemberInputData`、`ModifyMemberEmailInputData`

## `com.example.member.application.port.output`

- 職責：定義輸出埠的資料傳輸物件，用於返回處理結果
- 實作原則：
    - 所有類別命名必須以 `OutputData` 結尾
    - 封裝查詢結果和處理狀態
    - 例如：`QueryMemberOutputData`

## `com.example.member.application.usecase.command`

- 職責：處理命令操作的用例實作（寫入操作）
- 實作原則：
    - 實作 CQRS 的 Command 端
    - 處理創建、修改、刪除等寫入操作
    - 發布領域事件
    - 例如：`MemberCommandUseCase`

## `com.example.member.application.usecase.query`

- 職責：處理查詢操作的用例實作（讀取操作）
- 實作原則：
    - 實作 CQRS 的 Query 端
    - 只處理資料查詢，不修改狀態
    - 可直接訪問讀取優化的資料源
    - 例如：`MemberQueryUseCase`