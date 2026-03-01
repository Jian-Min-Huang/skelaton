# GENERAL_RULE

## Guidelines

- Clean Architecture, Functional DDD, CQRS
- each boundary context should be in its own package, and each layer (domain, application, presentation, infrastructure)
  should be in its own subpackage
- final arguments in methods
- no var, declare with type and final modifier
- no primitive type, use wrapper class instead
- all date or time fields should use java.time.Instant
- least knowledge principle, only expose necessary methods and fields from inside to outside
- layout for single field record class

```java
public record SingleFieldRecordClass(String args) implements SomeInterface {
    // methods
}
```

- layout for multiple fields record class

```java
public record MultipleFieldRecordClass(
        String args1,
        Integer args2,
        BigDecimal args3
) implements SomeInterface {
    // methods
}
```

- layout for single arguments method

```java
public void singleArgsMethod(final String arg) {
    // method body
}
```

- layout for multiple arguments method, each argument on a new line, aligned with the opening parenthesis of the method
  declaration, and the closing parenthesis on a new line after the last argument

```java
public void multipleArgsMethod(final String arg1,
                               final Integer arg2,
                               final BigDecimal arg3) {
    // method body
}
```

## Package Structure

- com.example.inventory.domain.product
- com.example.inventory.domain.product.entity
- com.example.inventory.domain.product.enu
- com.example.inventory.domain.product.event
- com.example.inventory.domain.product.repository
- com.example.inventory.domain.product.vo
- com.example.inventory.domain.warehouse
- com.example.inventory.domain.warehouse.entity
- com.example.inventory.domain.warehouse.enu
- com.example.inventory.domain.warehouse.event
- com.example.inventory.domain.warehouse.repository
- com.example.inventory.domain.warehouse.vo
- com.example.inventory.domain.service
- com.example.inventory.application
- com.example.inventory.application.command
- com.example.inventory.application.command.output
- com.example.inventory.application.command.assembler
- com.example.inventory.application.gateway
- com.example.inventory.application.handler
- com.example.inventory.application.query
- com.example.inventory.application.query.output
- com.example.inventory.application.query.assembler
- com.example.inventory.infrastructure.adapter
- com.example.order.domain.cart
- com.example.order.domain.cart.entity
- com.example.order.domain.cart.enu
- com.example.order.domain.cart.event
- com.example.order.domain.cart.repository
- com.example.order.domain.cart.vo
- com.example.order.domain.order
- com.example.order.domain.order.entity
- com.example.order.domain.order.enu
- com.example.order.domain.order.event
- com.example.order.domain.order.repository
- com.example.order.domain.order.vo
- com.example.order.domain.service
- com.example.order.application
- com.example.order.application.command
- com.example.order.application.command.output
- com.example.order.application.command.assembler
- com.example.order.application.gateway
- com.example.order.application.handler
- com.example.order.application.query
- com.example.order.application.query.output
- com.example.order.application.query.assembler
- com.example.order.infrastructure.adapter
