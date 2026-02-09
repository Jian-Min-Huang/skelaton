# Spring Web Backend Skills

## 1. General Principles

- Three-tier architecture: Controller (API layer), Service (business logic), Repository (data access)
- Test-driven development with unit tests at the service layer
- RESTful API design
- Interface segregation and dependency inversion principles

## 2. Tech Stack

- Java 21
- Spring Web MVC
- Jakarta Validation
- SpringDoc OpenAPI
- Lombok
- JUnit 5
- Mockito
- AssertJ

## 3. Package Structure

Organize code by **domain feature**, not by technical layer.

- Each domain (e.g., Skill, User, Order) has its own package under `com.example.<domain>`
- Inside each domain package, create subpackages for `controller`, `service`, and `repository`
- The controller package contains subpackages and classes for:
    - `protocol` (API interfaces)
    - `request` and `response` DTOs
    - `converter` for mapping between entities and DTOs
    - <Domain>Controller implements the <Domain>Protocol interface
- The service package contains subpackages and classes for:
    - `entity` (immutable domain objects)
    - `vo` (immutable value objects)
    - `enu` (enums)
    - <Service>Service implements business logic and interacts with repositories
- The repository package contains only interfaces for data access (no implementation)

### 3.1 Protocol Interface

Each domain defines a **Protocol interface** for the API contract. The Protocol may have these annotations:

- OpenAPI annotations for documentation
    - `@Tag` for the interface, `@Operation` for each method, `@Parameter` and `@Schema` for parameters and fields
- Spring Web annotations for routing
    - `@GetMapping`, `@PostMapping`, etc. on methods, `@RequestParam`, `@PathVariable`, `@RequestBody` on parameters
- Validation annotations for request parameters
    - Spring validation constraints `@Validated` and Jakarta constraints (e.g., `@Positive`, `@NotBlank`) on path/query
      parameters

ref

**Rules:**

- URL pattern: `/api/v1/<resource-plural>` (versioned, plural nouns)
- HTTP verbs: `POST` = create, `GET` = query, `PUT` = modify, `DELETE` = remove
- All method parameters must be `final`

### 3.2. Request DTOs

Request DTOs are **mutable** (needed for Jackson deserialization) and carry all validation rules.

ref

**Rules:**

- `@Data` + `@NoArgsConstructor` + `@AllArgsConstructor` + `@Builder(toBuilder = true)`
- Every constraint annotation must include a descriptive `message`
- Every field must include `@Schema(example = "...")` for OpenAPI docs
- Fields are `private`

### 3.3. Response DTOs

Response DTOs are **immutable** and carry only OpenAPI documentation — no validation.

ref

**Rules:**

- `@Value` + `@Builder` (no `toBuilder`, no `@NoArgsConstructor`)
- Every field must include `@Schema(description = "...", example = "...")`
- Include `createTime` and `lastModifyTime` audit fields
- Fields are declared without access modifiers

### 3.4. Converter

A dedicated `@Component` class per domain handles all Entity <-> DTO mapping.

ref

**Rules:**

- Method naming: `toEntity(...)` for request-to-entity, `toResponseDto(...)` for entity-to-response
- Manual field-by-field mapping using builders
- All parameters must be `final`
- No business logic — pure data transformation only
- Audit fields (timestamps, etc.) are **not** set in the converter; the repository handles them

### 3.5. Controller Layer

Controllers implement the Protocol interface and orchestrate converter + service calls.

ref

**Rules:**

- `@RestController` + `@RequiredArgsConstructor` + `@Log4j2`
- Implements the corresponding `Protocol` interface — no routing/validation annotations on the controller itself
- All dependencies are `private final`; all parameters are `final`
- Logging pattern: `log.info("ControllerName, methodName, paramName: {}", value)` before and after the service call
- HTTP response conventions:

| Operation         | Success                               | Not Found       |
|-------------------|---------------------------------------|-----------------|
| Create            | `201 Created` with `Location` header  | —               |
| Query by ID       | `200 OK` with body                    | `404 Not Found` |
| Query by criteria | `200 OK` with list (empty list is OK) | —               |
| Modify            | `204 No Content`                      | `404 Not Found` |
| Remove            | `204 No Content`                      | `404 Not Found` |

## 3.6. Entity Layer

Entities are **immutable value objects** living in `service.entity`.

ref

**Rules:**

- `@Value` for immutability (all fields become `private final`)
- `@Builder(toBuilder = true)` for construction and partial copies
- `@With` for single-field modifications (e.g., `entity.withId(1L)`)
- Fields are declared without access modifiers (package-private source, `private final` after Lombok)
- Every entity includes the common audit fields listed above
- No JPA annotations — entities are plain POJOs

## 3.7. Enum Pattern

ref

**Rules:**

- `@Getter` + `@RequiredArgsConstructor(access = AccessLevel.PRIVATE)`
- Each constant has a numeric `val` field
- Provide a `static getByVal(int)` lookup method
- Trailing comma after the last constant

## 3.8. Enum Pattern

ref

**Rules:**

- `@Getter` + `@RequiredArgsConstructor(access = AccessLevel.PRIVATE)`
- Each constant has a numeric `val` field
- Provide a `static getByVal(int)` lookup method
- Trailing comma after the last constant

## 3.9. Service Layer

Thin services that contain business logic and delegate persistence to the repository.

ref

**Rules:**

- `@Service` + `@RequiredArgsConstructor` for constructor injection
- All dependencies are `private final`
- All parameters are `final`
- Method naming: `createX`, `queryXById`, `queryXsByCriteria`, `modifyX`, `removeX`, `existsById`
- Modify pattern: fetch existing entity -> `toBuilder()` -> overwrite changed fields -> `build()` -> `update()`
- Return `Optional<Entity>` for single lookups, `List<Entity>` for collections, `Optional<Integer>` for updates
- Services accept and return **entities only** — never DTOs

## 3.10. Repository Layer

Plain Java interfaces

ref

**Rules:**

- Interface only in `repository/`;

## 4. Testing

Only **unit tests** at the service layer. Use **Mockito** for mocking and **AssertJ** for assertions.

ref

**Rules:**

- Test class naming: `<ClassName>Tests` (plural)
- Test method naming: `methodName_Scenario_ExpectedBehavior` (e.g., `querySkillById_WhenSkillExists_ShouldReturnSkill`)
- Use `@ExtendWith(MockitoExtension.class)` — no `@SpringBootTest`
- Instantiate the service under test manually in `@BeforeEach` (do not use `@InjectMocks`)
- Use `@Mock` for dependencies
- Follow **Given / When / Then** structure with comments
- Use AssertJ `assertThat(...)` — never JUnit `assertEquals()`
- Use `verify(mock, times(n)).method(...)` to confirm interactions
- For `BigDecimal` comparisons use `isEqualByComparingTo()`
- Cover: happy path, not-found/empty cases, and edge cases

## 5. TDD Workflow

## 6. General Conventions

| Rule                            | Detail                                                                                                     |
|---------------------------------|------------------------------------------------------------------------------------------------------------|
| **`final` everywhere**          | All method parameters, local variables where possible, and injected fields must be `final`                 |
| **Constructor injection**       | Always via `@RequiredArgsConstructor` — never field injection (`@Autowired`)                               |
| **Logging**                     | `@Log4j2` on controllers and services that need logging                                                    |
| **No global exception handler** | Exception handling is done locally in the service/controller layer as needed                               |
| **Soft delete**                 | Entities carry a `deleted` flag and associated audit fields (`deletedBy`, `deleteTime`)                    |
| **Validation boundary**         | Validation occurs only at the API boundary (Protocol/Controller layer) — never in services or repositories |
| **No mapping frameworks**       | Manual field mapping via builder in Converter classes                                                      |
| **Immutability by default**     | Entities and response DTOs are immutable (`@Value`); only request DTOs are mutable (`@Data`)               |
