# Presentation Layer

## `com.example.member.presentation.http.converter`

- 職責：資料轉換器，負責應用層資料物件與表現層資料物件的轉換
- 實作原則：
    - 應用層資料物件會定義在這些 `package` 裡
        - `com.example.member.application.adapter.vo.*`
        - `com.example.member.application.adapter.vo.enu.*`
        - `com.example.member.application.port.input.*`
        - `com.example.member.application.port.output.*`
    - 表現層資料物件會定義在這些 `package` 裡
        - `com.example.member.presentation.http.dto.*`
        - `com.example.member.presentation.http.dto.enu.*`
        - `com.example.member.presentation.http.request.*`
        - `com.example.member.presentation.http.response.*`
- 範例檔案：
    - `MemberConverter`

## `com.example.member.presentation.http.request`

- 職責：HTTP 請求物件
- 實作原則：
    - 需要加上 `Lombok Framework` 的註解
        - 例如：
            - `@Builder(toBuilder = true)`
            - `@Data`
            - `@NoArgsConstructor`
            - `@AllArgsConstructor`
  - 使用以下框架來進行對應的標示與驗證註解
      - `springdoc-openapi-ui`
      - `jakarta.validation-api`
- 範例檔案：
    - `CreateMemberRequest`
    - `ModifyMemberEmailRequest`
    - `QueryMemberEmailRequest`

## `com.example.member.presentation.http.response`

- 職責：HTTP 回應物件
- 實作原則：
    - 需要加上 `Lombok Framework` 的註解
        - 例如：
            - `@Builder(toBuilder = true)`
            - `@Data`
            - `@NoArgsConstructor`
            - `@AllArgsConstructor`
  - 使用以下框架來進行對應的標示與驗證註解
      - `springdoc-openapi-ui`
      - `jakarta.validation-api`
- 範例檔案：
    - `QueryMemberResponse`

## `com.example.member.presentation.http.dto`

- 職責：表現層使用的資料傳輸物件
- 實作原則：
    - 需要加上 `Lombok Framework` 的註解
        - 例如：
            - `@Builder(toBuilder = true)`
            - `@Data`
            - `@NoArgsConstructor`
            - `@AllArgsConstructor`
    - 使用以下框架來進行對應的驗證註解
        - `jakarta.validation-api`
- 範例檔案：
    - `PhoneNumberDto`

## `com.example.member.presentation.http.dto.enu`

- 職責：表現層使用的列舉資料傳輸物件
- 實作原則：
    - 對外暴露的列舉資料傳輸物件
    - 遵守最小知識原則
- 範例檔案：
    - `GenderEnuDto`
    - `MemberStatusEnuDto`

## `com.example.member.presentation.http.protocol`

- 職責：定義 REST API 的端點
- 實作原則：
    - 使用以下框架來進行對應的標示與驗證註解
        - `spring-webmvc`
        - `springdoc-openapi-ui`
        - `jakarta.validation-api`
- 範例檔案：
    - `MemberProtocol`

## `com.example.member.presentation.http.route`

- 職責：實作 REST API 的端點
- 實作原則：
    - 需要實作 `MemberProtocol` 介面
    - 使用以下框架來進行對應的標示與驗證註解
        - `spring-webmvc`
        - `springdoc-openapi-ui`
- 範例檔案：
    - `MemberController`
