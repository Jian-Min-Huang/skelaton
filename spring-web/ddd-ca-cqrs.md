# ddd-ca-cqrs

這份文件定義了基於 `Domain Driven Design`、`Clean Architecture` 和 `Command Query Responsibility Segregation` 的 Spring
Boot 專案架構標準。

## 領域劃分原則

每個業務領域都應該獨立成一個完整的模組，方便未來獨立部署成微服務的需求，例如：

- `com.example.member.*` - 會員領域
- `com.example.product.*` - 商品領域
- `com.example.order.*` - 訂單領域

## 套件結構與職責說明

以 `member` 領域為例，說明各層級的職責與實作原則：

- [domain layer](domain.md)
- [application layer](application.md)
- [presentation layer](presentation.md)
- [infrastructure layer](infrastructure.md)
