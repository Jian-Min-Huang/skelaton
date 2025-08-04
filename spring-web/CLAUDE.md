# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Architecture Overview

This is a Clean Architecture-based Spring Boot application implementing CQRS (Command Query Responsibility Segregation)
pattern with DDD (Domain-Driven Design) principles.

### Core Architectural Patterns

1. **Clean Architecture Layers**:
    - **Presentation**: HTTP controllers, DTOs, converters, protocols
    - **Application**: Use cases, ports (input/output interfaces), adapters
    - **Domain**: Entities, value objects, repositories, events
    - **Infrastructure**: Database implementations, configurations, external services

2. **CQRS Implementation**:
    - Commands and queries are separated through `CqrsTemplate` interface
    - All use cases implement `CqrsTemplate` extending `UseCase<CqrsInput<?>, CqrsOutput<?>>`
    - Input/output handling uses `CqrsInput` and `CqrsOutput` wrappers
    - Exit codes (`ExitCode.SUCCESS`/`ExitCode.FAILURE`) for operation status

### Package Structure by Domain

Each domain (currently `member`) follows this structure:

- `application/`: Use cases, ports, adapters, VOs
    - `usecase/command/`: Command operations (create, modify, remove)
    - `usecase/query/`: Query operations (read, search)
    - `port/input/`: Input interfaces and DTOs
    - `port/output/`: Output interfaces and DTOs
    - `adapter/`: Domain model projectors and converters
- `domain/`: Core business logic
    - `entity/`: Domain entities
    - `repository/`: Repository interfaces (readonly/writable separation)
    - `vo/`: Value objects and enums
    - `event/`: Domain events
- `infrastructure/`: External concerns
    - `config/`: Spring configuration and bean definitions
    - `persistence/`: Database access (DAOs, mappers, POs)
    - `repository/`: Repository implementations
    - `eventbus/`: Event bus implementation
- `presentation/`: External interfaces
    - `http/`: REST API (controllers, DTOs, requests/responses)
    - `ipc/`: Inter-process communication (for cross-domain access)

### Key Architectural Constraints

The codebase enforces architectural rules via ArchUnit tests in `DependencyTests.java`:

1. **Layer Dependencies**:
    - Presentation layer cannot be accessed by any other layer
    - Application layer only accessed by Presentation and Infrastructure
    - Domain layer only accessed by Application and Infrastructure

2. **Domain Isolation**:
    - Domains can only depend on other domains through their `presentation.ipc` packages
    - No direct cross-domain dependencies allowed

3. **Package Cycles**: No circular dependencies between packages

### Repository Pattern

- **Separation of Concerns**: Read operations use `ReadonlyRepository`, write operations use `WritableRepository`
- **Generic Types**: Repositories are parameterized `<EntityType, IdType>`
- **Implementation**: Located in infrastructure layer, interfaces in domain layer

### Configuration

- **Application Properties**: Database connections for PostgreSQL, MongoDB, Redis
- **Docker Compose**: Multi-database setup with persistent volumes
- **Bean Configuration**: Use cases are manually configured as Spring beans in domain-specific `Configuration` classes
- **Testcontainers**: Used for integration testing with real database instances

### Testing Strategy

- **Architecture Tests**: Validates clean architecture constraints using ArchUnit
- **Unit Tests**: Use case testing with mocked dependencies
- **Integration Tests**: Uses Testcontainers for database testing
- **Test Structure**: Mirrors main source structure under `src/test/java`