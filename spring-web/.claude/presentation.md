# Presentation Layer

## `com.example.member.presentation.http.converter`

- 職責：資料轉換器，負責應用層資料物件與 `HTTP` 層請求回應和資料傳輸物件的轉換
- 實作原則：
    - 例如：`MemberConverter`

## `com.example.member.presentation.http.request`

- 職責：HTTP 請求物件
- 實作原則：
    - 需要加上 `Lombok Framework` 的註解
        - 例如：
            - `@Builder(toBuilder = true)`
            - `@Data`
            - `@NoArgsConstructor`
            - `@AllArgsConstructor`
    - 需要根據欄位的業務屬性加上 `jakarta.validation` 的註解
        - 例如：
            - `@NotNull`
            - `@NotBlank`
            - `@Email`
            - `@Positive`
            - `@Valid`
    - 需要加上 `OpenAPI` 的註解
        - 例如：
            - `@Schema`
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
    - 需要加上 `OpenAPI` 的註解
        - 例如：
            - `@Schema`
- 範例檔案：`QueryMemberResponse`

## `com.example.member.presentation.http.dto`

- 職責：HTTP 層使用的資料傳輸物件
- 實作原則：
    - 輕量級資料容器
    - 包含序列化註解
    - 例如：`PhoneNumberDto`

## `com.example.member.presentation.http.dto.enu`

- 職責：HTTP 層使用的列舉資料傳輸物件
- 實作原則：
    - 對外暴露的列舉格式
    - 可能與領域層列舉不同
    - 例如：`GenderEnuDto`、`MemberStatusEnuDto`

## `com.example.member.presentation.http.protocol`

- 職責：定義 REST API 協議和回應格式
- 實作原則：
    - 使用 `Spring Web MVC`、`Spring OpenAPI` 和 `jakarta.validation`
    - 例如：`MemberProtocol`

#### `com.example.member.presentation.http.route`

- 職責：REST API 控制器
- 實作原則：
    - 使用 `Spring Web MVC` 和 `Spring OpenAPI` 來實作
    - 需要實作 `MemberProtocol` 介面，該介面詳細定義 `API` 的路由，輸入輸出格式與文件註解
    - 例如：
        - `@RestController`
        - `@Tag`
    - 例如：`MemberController`
