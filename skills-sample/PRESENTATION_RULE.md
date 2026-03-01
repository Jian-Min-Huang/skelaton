# PRESENTATION_RULE

## HTTP

- HTTP API 的版本號放在 URL 路徑中（如 `/v1/products`），而非放在 Header 中，確保 API 的可發現性和易用性
- URL 路徑設計遵循 RESTful 原則，使用複數動名詞表示資源集合（如 `/v1/products`），使用 `{id}` 表示特定資源（如 `/v1/products/{id}`）
- 非 CRUD 的操作則使用冒號分隔的自定義動作名稱（如 `/v1/products/{id}:activate`），避免濫用 HTTP 動詞（如 POST /deactivate）造成語意不清

### Request DTO

- 用 `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` 定義 Request DTO，並實作 `RequestDTO` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/presentation/http/request/CreateProductRequestDTO.java`

### Request Converter

- 用 `@Component` 標注 Request Converter 類別並實作 `RequestConverter` 介面來做為標記
- Request Converter 的職責為轉換 Request DTO 為 CQRS Command 或 CQRS Query
- reference code: `src/main/java/com/example/inventory/presentation/http/request/converter/ProductRequestConverter.java`

### Response

- 用 `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` 定義 Response DTO，並實作 `ResponseDTO` 介面來做為標記
- reference code: `src/main/java/com/example/inventory/presentation/http/response/ProductResponseDTO.java`

### Response Converter

- 用 `@Component` 標注 Response Converter 類別並實作 `ResponseConverter` 介面來做為標記
- Response Converter 的職責為轉換 CQRS Command Output 或 CQRS Query Output 為 Response DTO
- reference code: `src/main/java/com/example/inventory/presentation/http/response/converter/ProductResponseConverter.java`

### Protocol

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
- reference code: `src/main/java/com/example/inventory/presentation/http/ProductController.java`
