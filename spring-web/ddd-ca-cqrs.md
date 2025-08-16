# Domain Driven Design, Clean Architecture, Command Query Responsibility Segregation

## package structure

e.g. assume we have three domains: member, product, order

- com.example.member.*
- com.example.product.*
- com.example.order.*

## package of each domain

e.g. use member domain as an example

- com.example.member.application.adapter.projector
- com.example.member.application.adapter.vo.enu
- com.example.member.application.adapter.vo
- com.example.member.application.port.input
- com.example.member.application.port.output
- com.example.member.application.usecase.command
- com.example.member.application.usecase.query
- com.example.member.domain.entity
- com.example.member.domain.event
- com.example.member.domain.repository.readonly
- com.example.member.domain.repository.writable
- com.example.member.domain.vo.enu
- com.example.member.domain.vo
- com.example.member.infrastructure.config
- com.example.member.infrastructure.eventbus
- com.example.member.infrastructure.persistence.dao
- com.example.member.infrastructure.persistence.mapper
- com.example.member.infrastructure.persistence.po
- com.example.member.infrastructure.repository
- com.example.member.presentation.http.converter
- com.example.member.presentation.http.dto.enu
- com.example.member.presentation.http.dto
- com.example.member.presentation.http.protocol
- com.example.member.presentation.http.request
- com.example.member.presentation.http.response
- com.example.member.presentation.http.route

## responsibiliy of each package

### Presentation

#### Protocol

#### Route

#### Request

#### Response

#### Data Transfer Object

#### Data Transfer Object Enum

#### Converter

### Application

#### Command Use Case

#### Query Use Case

#### Input Port

#### Output Port

#### Value Object

#### Value Object Enum

#### Projector Adapter

### Domain

#### Entity

#### Event

#### Readonly Repository

#### Writable Repository

#### Value Object

#### Value Object Enum

### Infrastructure

#### Config

#### Event Bus

#### Data Access Object

#### Mapper

#### Persistent Object

#### Repository