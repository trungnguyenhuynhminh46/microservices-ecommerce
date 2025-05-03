# 🛒 Ecommerce Microservices System

This is a backend project for an ecommerce platform designed using **microservices architecture**. The system is built to be **modular**, **scalable**, and **event-driven**, applying key architectural patterns such as:

- ✅ **Microservices** – to decompose the application into smaller, manageable services.
- ✅ **Event-Driven Architecture** – to enable asynchronous communication between services.
- ✅ **Domain-Driven Design (DDD)** – to model the business domain effectively.
- ✅ **Saga Pattern** – to manage distributed transactions across services.
- ✅ **Outbox Pattern** – to ensure reliable event delivery.
- ✅ **CQRS (Command Query Responsibility Segregation)** – to optimize for scalability and performance.
- ✅ **Clean and Hexagonal Architecture** – to maintain separation of concerns and testability.
- ✅ **Database per Service** – to ensure data ownership and independence.
- ✅ **Kafka** – to facilitate asynchronous communication between services.

This project consists of multiple backend APIs responsible for managing product information, user data, and inventory. The core focus is on the order placement flow, as it represents a complex, cross-service use case that involves coordination across multiple microservices.

---

## 💡 Motivation

The motivation behind this project stems from my desire to gain practical experience in designing distributed systems that are not only functional, but also scalable, resilient, and aligned with real-world architectural principles. Rather than stopping at theoretical knowledge, I wanted to build a system that forces me to solve problems commonly encountered in production — such as service coordination, data consistency, and domain complexity.

A central goal of this project is to internalize and apply **Domain-Driven Design (DDD)**. DDD provides a powerful way to break down a complex business problem into meaningful components that reflect the reality of the domain. In this project, I structured services around bounded contexts, defined aggregates carefully to maintain transactional boundaries, and used value objects and entities to model real business behavior. More importantly, DDD encouraged clear communication between code and business logic — allowing the technical design to evolve naturally from the problem space.

To manage the complexity of cross-service transactions, I applied the **Saga Pattern**, particularly using the choreography approach. Instead of relying on a central coordinator, services communicate via domain events to react and proceed in the transaction chain. This allows each service to stay autonomous while still participating in a long-running business process, such as placing an order. The compensation logic ensures that partial failures are gracefully handled, which is essential in a distributed environment.

In support of reliable event-driven communication, I implemented the **Outbox Pattern**. This pattern solves the common problem of atomicity between database writes and message publishing. By first storing domain events in a local outbox table within the same database transaction, and then asynchronously publishing them to Kafka, the system avoids inconsistencies between state and side effects — a crucial requirement for maintaining integrity across microservices.

Overall, this project is more than just an academic exercise. It’s a focused effort to deeply understand and apply modern backend engineering principles in a practical, hands-on way. Through it, I’ve learned to design better systems, think more clearly about architectural trade-offs, and prepare myself to handle the challenges of building reliable, maintainable software at scale.

---

## 🏗️ System Overview

The platform is composed of the following services:

| Service              | Responsibility                                |
|----------------------|-----------------------------------------------|
| **IdentityService**  | Manages user registration and authentication  |
| **ProfileService**   | Manages user profiles and preferences         |
| **ProductService**   | Handles product catalog and product info      |
| **InventoryService** | Manages stock levels and reservations         |
| **OrderService**     | Handles order creation and orchestration      |
| **PaymentService**   | Simulates payment gateway interaction         |

Other components

| Component       | Responsibility                                                    |
|-----------------|-------------------------------------------------------------------|
| **Api gateway** | Handles incoming requests and routes them to appropriate services |
| **Kafka**       | Message broker for asynchronous communication between services    |

All services communicate asynchronously using **Apache Kafka**, and each service manages its own database (**Database per Service** pattern).

### Run the following command to generate the dependency graph:

```bash
 mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.tuber*:*"
```
![dependency-graph.png](resources%2Freadme%2Fdependency-graph.png)

---

## 🎯 Nonfunctional Requirements

The system must meet the following nonfunctional requirements to ensure high performance, maintainability, and reliability:

- **Scalability**: The architecture must support horizontal scaling to handle increased load during peak usage.
- **Availability**: The system should maintain a high availability rate using redundancy and fault-tolerant services.
- **Resilience**: One failure should not cascade to other services. Each service should be able to recover independently.
- **Performance**: The apis should be fast and responsive, with low latency and high throughput.
- **Maintainability**: The codebase must follow clean architecture principles and be modular to enable easy updates and testing.
- **Consistency**: Eventual consistency should be ensured across microservices through asynchronous communication patterns (e.g., Kafka + Outbox pattern).
- **Message Driven**: The system should be designed to handle asynchronous events and messages, allowing for decoupled communication between services.

---

## ✅ Functional Requirements

The application is expected to support the following core functionalities:

- User registration, login, and authentication.
- Managing user profiles including view, update, and deactivate.
- Product listing with detailed information and availability.
- Inventory tracking and adjustment on order placement or cancellation.
- Placing, viewing, and cancelling customer orders.
- Payment processing and status tracking.
- Order status updates and state transitions.
- Admin interfaces for product, inventory, and order management.

---

## 🚀 Features

This system includes a rich set of features to deliver a seamless e-commerce experience:

- **User Management**: Secure registration, authentication (OAuth2), and profile updates.
- **Product Catalog**: Manage products static information.
- **Order Processing**: Support placing orders.
- **Payments Integration**: Seamless simulated payment integration and status tracking.
- **Inventory Management**: Automatic stock deduction and validation during order placement, manage export and import goods.
- **Asynchronous Messaging**: Kafka-based event communication between services (saga pattern).
- **Outbox**: Reliable messaging with Outbox pattern for consistency across services.
- **Role-based Access Control (RBAC)**: Protect sensitive operations with role permissions.

---

## 🏗️ Architecture Overview

This is a simplified e-commerce system built with Spring Boot, following the microservices architecture.
The system consists of 6 independent services, each with its own dedicated database, ensuring clear separation of concerns and better scalability. These services communicate with each other asynchronously via Kafka as the message broker, enabling reliable message delivery and improving the system's fault tolerance.

- The below image illustrates the architecture of the system, including Gateway, several services, message brokers, databases, caching, ...

![tuber diagram.jpg](resources%2Freadme%2Ftuber%20diagram.jpg)

---

### Identity Service

![Identity Service.png](resources%2Freadme%2FIdentity%20Service.png)

---

### Profile Service

![Profile Service.png](resources%2Freadme%2FProfile%20Service.png)

---

### Product Service

![Product Service.png](resources%2Freadme%2FProduct%20Service.png)

---

### Inventory Service

![Invetory Service.png](resources%2Freadme%2FInvetory%20Service.png)

---

### Payment Service

---

### Order Service

![Order Service.png](resources%2Freadme%2FOrder%20Service.png)

---

## 🔄 Order State transitions

This system defines an application that operates across five distinct states:
- **PENDING**: The initial state triggered when a request is first submitted to the system.
- **PAID**: The state entered once the customer successfully completes the payment.
- **APPROVED**: The state achieved when the restaurant confirms the order after payment is processed.
- **CANCELLING**: An intermediate state indicating the order is in the process of being canceled, which may occur if the restaurant rejects the order or the customer requests cancellation, requiring a refund.
- **CANCELLED**: A final state where order processing ceases due to non-payment, restaurant rejection, or customer-initiated cancellation.

The image below illustrates the order state machine.

![State transitions.jpg](resources%2Freadme%2FState%20transitions.jpg)
---

## 🛒 Order Placement Flow
When a send a request in order to create a new order. The process create and complete the order basically consists of two main stages:
1. **Complete payment**: The Order Service send a complete payment request for the payment service.
2. **Inventory confirmation**: The Order Service send a confirmation request to the Inventory Service to confirm if the goods are available for fulfillment.

Despite the simplicity of these two stages, the microservices-based architecture, which delegates tasks across multiple services, demands robust mechanisms to maintain transaction consistency and address potential errors. To achieve this, the solution implements the SAGA pattern. The diagram provided below outlines the complete transaction process, encompassing a total of 14 distinct steps.

![Screenshot 2025-05-03 at 15.18.11.png](Screenshot%202025-05-03%20at%2015.18.11.png)

---

## 📋 Prerequisites
- [Java 21](https://www.oracle.com/technetwork/pt/java/javase/downloads/index.html)
- [Maven 3.9.x+](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/)
- [Apache Kafka](https://kafka.apache.org/)
- Docker
- Docker-Compose
- [Rancher Desktop](https://rancherdesktop.io/) or [Docker Desktop](https://www.docker.com/products/docker-desktop/) for Windows platform (optional)
- [Postman](https://www.postman.com/) (optional)

---

## 🧩 Dependencies

This project leverages a variety of essential libraries and frameworks to support robust, scalable, and maintainable development across its microservices architecture.

### Core Dependencies

- **Spring Boot Starter Web**  
  Enables the development of RESTful web applications. It simplifies the configuration and setup of web servers and API routing.

- **Spring Boot Starter Validation**  
  Provides support for bean validation using Hibernate Validator. Useful for validating incoming requests and ensuring data integrity.

- **Spring Boot Starter Data JPA**  
  Simplifies data access with the Java Persistence API (JPA), especially when working with databases such as PostgreSQL.

- **Spring TX**  
  Provides programmatic and declarative transaction management for Spring-based applications.

- **Spring Kafka**  
  Integrates the Apache Kafka messaging system with Spring applications, enabling event-driven architecture and asynchronous communication.

- **Kafka Avro Serializer**  
  Used for serializing and deserializing Kafka messages using Avro schemas, ensuring message format consistency and schema evolution.

### Logging and Monitoring

- **Spring Boot Starter Logging (Logback)**  
  Provides default logging support using Logback. Central to debugging, monitoring, and tracing across services.

- **Log4J (Optional/Additional)**  
  Can be integrated for advanced logging configurations, especially in larger or legacy systems.

### Developer Productivity

- **Lombok**  
  Reduces boilerplate code by generating getters, setters, constructors, and more at compile time. Improves code readability and maintainability.

- **MapStruct**  
  A code generator that greatly simplifies the implementation of mappings between Java bean types.

- **Spring Boot DevTools**  
  Improves developer experience with features like automatic restarts, live reload, and configurations for development-only environments.

### Cloud-Native Support

- **Spring Cloud OpenFeign**  
  Declarative REST client that simplifies HTTP client creation for inter-service communication in a microservices environment.

- **Spring Cloud Dependencies**  
  Provides a consistent version set of Spring Cloud projects, including Config, Eureka, Gateway, and more.

### Serialization

- **Jackson Databind**  
  Core library for converting Java objects to JSON and vice versa.

- **Jackson Databind Nullable**  
  Adds support for handling nullable JSON properties in a more explicit and type-safe way.

### Testing

- **Mockito**  
  A popular framework used for creating mock objects in unit tests, supporting test-driven development (TDD).


Each module within the monorepo (e.g., `identity-service`, `profile-service`, `order-service`, etc.) also defines internal dependencies (e.g., `*-application`, `*-domain-core`, `*-messaging`) following a Domain-Driven Design (DDD) approach to enforce clear separation of concerns.

---

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/your-username/ecommerce-microservices.git
cd ecommerce-microservices

# Start all services
docker-compose up --build
``` 
---
## 📬 API Documentation
