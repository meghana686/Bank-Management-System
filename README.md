# 🏦 Bank Management System

A RESTful backend application developed using **Java and Spring Boot** to manage banks, addresses, and customer accounts. The application follows a layered architecture and uses Spring Data JPA, Hibernate, and PostgreSQL for data persistence.

---

## 📌 Project Overview

The Bank Management System is designed to simplify banking operations through REST APIs. It provides functionality for managing bank details, maintaining address information, and handling customer accounts.

The project demonstrates practical implementation of RESTful web services, object-relational mapping, entity relationships, input validation, and exception handling.

---

## 🚀 Features

### 🏢 Bank Management
- Create and retrieve bank records.
- Update bank details.
- Delete bank records with business validations.
- Search banks using different criteria.
- Support pagination and sorting.

### 📍 Address Management
- Create and retrieve address records.
- Search addresses by city, street, and pincode.
- Retrieve address information associated with a bank.
- Update address details.

### 💳 Account Management
- Create and retrieve customer accounts.
- Update account information.
- Delete accounts with business validations.
- Search accounts using different criteria.
- Deposit and withdraw money.
- Transfer money between accounts.
- Retrieve accounts by bank and account type.

### ⚙️ Additional Capabilities
- Custom exception handling.
- Input and business-rule validation.
- Entity relationships using JPA.
- Standardized API responses.
- Database persistence using PostgreSQL.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application development |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM and entity mapping |
| PostgreSQL | Relational database |
| Maven | Dependency management and build |
| Eclipse IDE | Development environment |
| Postman | API testing |

---

## 🏗️ Project Architecture

The application follows a layered architecture to separate responsibilities and improve maintainability.

- **Entity Layer:** Represents database entities and their relationships.
- **Repository Layer:** Handles database operations using Spring Data JPA.
- **Service Layer:** Implements business logic and validation.
- **REST Controller Layer:** Exposes REST endpoints and processes HTTP requests.
- **DTO Layer:** Defines structured API response objects.
- **Exception Layer:** Handles application errors and custom exceptions.

---

## 📂 Project Structure

```text
Bank-Management-System/
├── src/
│   └── main/
│       ├── java/
│       │   └── org/jsp/bank/
│       │       ├── entity/
│       │       ├── dto/
│       │       ├── repository/
│       │       ├── service/
│       │       ├── restcontroller/
│       │       └── exception/
│       └── resources/
│           └── application.properties
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

---

## 🗄️ Database Configuration

- **Database:** PostgreSQL
- **Database Name:** `BankDB`
- **Host:** `localhost`
- **PostgreSQL Port:** `5432`
- **Application Port:** `8080`

### Application Properties

Configure the following properties in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/BankDB
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Note:** Configure the `DB_PASSWORD` environment variable with your local PostgreSQL password. Never publish your actual password or other credentials on GitHub.

---

## 🧪 API Testing

Use **Postman** to test the application's REST APIs.

**Base URL:**

```text
http://localhost:8080
```

The APIs support bank, address, and account management operations, including CRUD operations, searching, sorting, and account transactions.

Detailed API endpoints and request/response examples can be added as the project is finalized.
🗄️ Database Configuration
Database: PostgreSQL
Database Name: BankDB
Host: localhost
Port: 8080
🧪 API Testing
Use Postman to test the application's REST APIs.
Base URL:
http://localhost:8080

Detailed endpoints and request/response examples can be documented here after finalizing the API
├── mvnw.cmd
└── pom.xml
