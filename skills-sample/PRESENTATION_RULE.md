# PRESENTATION_RULE

## 跨層依賴

- Presentation 層只允許依賴 Application 層（UseCase、Command、Query、Command Output、Query Output），不得直接依賴 Domain 層
- 原始型別（如 `String`）到 Domain 型別（如 `Category` enum、Value Object）的轉換由 Application 層的 UseCase 負責，Presentation 層的 Converter 只做 Request DTO → CQRS Command 的欄位對應，不涉及語意轉換

## HTTP

- HTTP API 的版本號放在 URL 路徑中（如 `/v1/products`），而非放在 Header 中，確保 API 的可發現性和易用性
- URL 路徑設計遵循 RESTful 原則，使用複數動名詞表示資源集合（如 `/v1/products`），使用 `{id}` 表示特定資源（如 `/v1/products/{id}`）
- 非 CRUD 的操作使用冒號分隔的自定義動作名稱，避免濫用 HTTP 動詞（如 POST /deactivate）造成語意不清，依據操作對象分為兩種模式：
  - **單一資源動作**：`/v1/products/{id}:activate`，資源 ID 在路徑中，適用於針對特定資源的操作，Request Body 攜帶額外參數（若有）
  - **集合層級動作**：`/v1/inventory:allocate-stock`，資源 ID 在 Request Body 中，適用於涉及多個資源或不屬於單一資源的操作（如庫存調撥需要 productId + warehouseId）

### Request DTO

- 用 `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` 定義 Request DTO，並實作 `RequestDTO` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/presentation/http/request/CreateProductRequestDTO.java`

### Request Converter

- 用 `@Component` 標注 Request Converter 類別並實作 `RequestConverter` 介面來做為標記
- Request Converter 的職責為轉換 Request DTO 為 CQRS Command
- reference code: `src/main/java/com/example/inventory/presentation/http/request/converter/ProductRequestConverter.java`

### Response DTO

- 用 `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` 定義 Response DTO，並實作 `ResponseDTO` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/presentation/http/response/ProductResponseDTO.java`

### Response Converter

- 用 `@Component` 標注 Response Converter 類別並實作 `ResponseConverter` 介面來做為標記
- Response Converter 的職責為轉換 CQRS Query Output 為 Response DTO
- reference code: `src/main/java/com/example/inventory/presentation/http/response/converter/ProductResponseConverter.java`

### Protocol

- Protocol 作為 API 合約介面，集中定義路由、OpenAPI 文件、參數驗證等宣告式配置；Controller 作為實作類別只負責流程協調，實現關注點分離
- 用 `interface` 定義 Protocol，並繼承 `Protocol` 介面來做為標記
- 用 `@Tag` 標注 Protocol 來做為 OpenAPI 的分組依據，每個標注要提供 name 和 description 屬性
- 用 `@Operation` 標注 Protocol 裡面的方法來說明 API 的行為，每個標注要提供 summary 屬性
- 用 `@ApiResponses` 標注 Protocol 裡面的方法來描述可能的回應狀態，每個 `@ApiResponse` 要提供 responseCode 和 description 屬性
- 用 `@GetMapping`, `@PostMapping`, `@DeleteMapping` 標注 Protocol 裡面的方法來定義路由和 HTTP 動詞，URL 路徑放在標注的 value 屬性中
- 不要使用 `@PutMapping` 和 `@PatchMapping`，而是使用 `@PostMapping` 來處理所有非 GET 和 DELETE 的操作，並在 URL 路徑中使用冒號分隔的自定義動作名稱來區分不同的操作（如 `/v1/products/{id}:activate`）
- 用 `@Validated` 標注 `@RequestBody` 以啟用請求體的驗證，並在 DTO 中使用 Jakarta Bean Validation 的註解（如 `@NotNull`, `@Size`, `@Min`, `@Max`）來定義驗證規則
- 用 `@Parameter` 標注 `@PathVariable` 和 `@RequestParam` 以提供參數的說明和驗證規則，每個標注要提供 description 屬性來說明參數的用途，並使用 Jakarta Bean Validation 的註解來定義驗證規則（如 `@NotNull`, `@Size`, `@Min`, `@Max`）
- reference code: `src/main/java/com/example/inventory/presentation/http/protocol/ProductProtocol.java`

### Controller

- 用 `@RestController`, `@RequiredArgsConstructor` 標注 Controller 類別並實作對應的 Protocol
- Controller 注入 Request Converter、Response Converter、Command Use Case、Query Use Case，依據操作類型選擇對應的流程：
  - **Command flow**：接收 Request DTO → Request Converter 轉為 CQRS Command → 呼叫 Command Use Case → 從 CQRS Command Output 建構 `ResponseEntity`（如 201 + Location header）
  - **Query flow**：從 `@PathVariable` / `@RequestParam` 直接構建 CQRS Query → 呼叫 Query Use Case → Response Converter 轉為 Response DTO → 回傳 200
- reference code: `src/main/java/com/example/inventory/presentation/http/ProductController.java`

### Exception Handler

- 用 `@Log4j2`, `@RestControllerAdvice` 標注全域例外處理類別
- 用 `@ExceptionHandler` 標注方法來處理特定例外類型
- 例外對應的 HTTP 狀態碼：
  - `DomainException` → 422 Unprocessable Entity（業務規則違反）
  - `MethodArgumentTypeMismatchException` → 400 Bad Request（/api/v1/users/{id}, id is a Long type but the request is /api/v1/users/abc）
  - `MethodArgumentNotValidException` → 400 Bad Request（Jakarta Bean Validation 驗證失敗）
  - `Exception` → 500 Internal Server Error（未預期錯誤，不暴露內部細節）
- Controller 不應自行 try-catch 例外，由 `@RestControllerAdvice` 統一攔截處理
- reference code: `src/main/java/com/example/shared/presentation/http/GlobalExceptionHandler.java`
