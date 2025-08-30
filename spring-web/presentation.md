### Presentation Layer (表現層)

#### `com.example.member.presentation.http.converter`

**職責**：資料轉換器，處理 DTO 與領域物件的轉換

- 實作原則：

- 使用 MapStruct 等工具進行轉換
- 處理不同層級間的資料格式轉換
- 例如：`MemberConverter`

#### `com.example.member.presentation.http.dto.enu`

**職責**：HTTP 層使用的列舉 DTO

- 實作原則：

- 對外暴露的列舉格式
- 可能與領域層列舉不同
- 例如：`GenderEnuDto`、`MemberStatusEnuDto`

#### `com.example.member.presentation.http.dto`

**職責**：HTTP 傳輸用的資料傳輸物件

- 實作原則：

- 輕量級資料容器
- 包含序列化註解
- 例如：`PhoneNumberDto`

#### `com.example.member.presentation.http.protocol`

**職責**：定義 HTTP API 協議和回應格式

- 實作原則：

- 統一的 API 回應格式
- 錯誤處理標準
- 例如：`MemberProtocol`

#### `com.example.member.presentation.http.request`

**職責**：HTTP 請求物件

- 實作原則：

- 包含驗證註解
- 對應 API 端點的輸入格式
- 例如：`CreateMemberRequest`、`ModifyMemberEmailRequest`

#### `com.example.member.presentation.http.response`

**職責**：HTTP 回應物件

- 實作原則：

- 標準化的回應格式
- 包含必要的業務資料
- 例如：`QueryMemberResponse`

#### `com.example.member.presentation.http.route`

**職責**：REST API 控制器

- 實作原則：

- 薄薄的一層，主要負責 HTTP 協議處理
- 呼叫應用服務處理業務邏輯
- 統一的異常處理和回應格式
- 例如：`MemberController`
