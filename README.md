
# Banking Microservices

This repository contains a **microservices-based** banking application along with a **Eureka Discovery Server**. The goal is to demonstrate how multiple Spring Boot services can register with Eureka and communicate with each other in a scalable, maintainable architecture.

---

## Table of Contents

1. [Overview](#overview)  
2. [Architecture](#architecture)  
3. [Projects](#projects)  
   - [1. Discovery Server](#1-discovery-server)  
   - [2. Banking System](#2-banking-system)  
4. [Prerequisites](#prerequisites)  
5. [Getting Started](#getting-started)  
6. [Usage & Endpoints](#usage--endpoints)  
   - [1. Eureka Discovery Server](#1-eureka-discovery-server)  
   - [2. Banking System](#2-banking-system)  
7. [Screenshots (Optional)](#screenshots-optional)  
8. [Contact](#contact)  
9. [License](#license)

---

## Overview

- **Eureka Discovery Server**: Acts as a registry where microservices (clients) can register themselves and discover other services.  
- **Banking System**: A Spring Boot microservice for user registration, login (JWT-based), and transaction handling. Utilizes Kafka for event-driven transaction processing.

### Key Features

- **Service Discovery**: All microservices register with Eureka.  
- **JWT Security**: The banking system uses JWT for secure endpoints.  
- **Database**: PostgreSQL for data persistence (Users, Transactions).  
- **Kafka**: Publishes transaction events asynchronously.  
- **Auditing**: Tracks creation and modification times of entities.  
- **Docker-Ready** (optional): If you containerize, you can easily spin up the environment with Docker Compose.

---

## Architecture

```
                            +-----------------------+
                            | Eureka Discovery      |
                            | (discovery-server)    |
                            +----------+------------+
                                       |
                                       |  Service Registry
                                       |
                   +-------------------+---------------------+
                   |                                         |
                   v                                         v
        +----------------------+                   +----------------------+
        | Banking System      |                   |    Future Services  |
        | (banking-system)    | <---- Kafka ----> |  (e.g., analytics)  |
        +----------+----------+                   +----------------------+
                   |
                   |  PostgreSQL
                   v
        +----------------------+
        |  bankingsystem DB    |
        +----------------------+
```

---

## Projects

### 1. Discovery Server

**Folder:** `discovery-server/`

- **Language/Framework**: Java 21, Spring Boot 3.x  
- **Description**:  
  - Provides a registry where other microservices (e.g., the banking system) can register and discover each other.  
  - Runs on port **8761** by default.  
  - Uses **`@EnableEurekaServer`** annotation.

**Key Files**  
- **`EurekaServerApplication.java`**: Main Spring Boot class that starts the Eureka Server.  
- **`application.properties`**: Configures server port (`8761`) and Eureka server settings.  
- **`pom.xml`**: Maven dependencies for Spring Boot and Eureka Server.

### 2. Banking System

**Folder:** `banking-system/`

- **Language/Framework**: Java 21, Spring Boot 3.x  
- **Description**:  
  - Handles user registration, login (JWT), and transaction creation.  
  - Publishes transaction events to Kafka.  
  - Registers itself with Eureka Discovery Server.  
  - Secured endpoints (requires JWT token).

**Key Files**  
- **`BankingSystemApplication.java`**: Main entry point for the banking system microservice.  
- **`SecurityConfig.java`**: Configures Spring Security and JWT filters.  
- **`JwtAuthenticationFilter.java`**: Extracts and validates JWT tokens from requests.  
- **`UserController.java`**: Handles user registration and login.  
- **`TransactionController.java`**: Manages transaction endpoints.  
- **`TransactionService.java`**: Business logic for transaction creation and Kafka event publishing.  
- **`UserService.java`**: Implements `UserDetailsService` for Spring Security.  
- **`application.properties`**: Sets server port (`8080`), DB (PostgreSQL), Eureka client settings, Kafka config.  
- **`pom.xml`**: Maven dependencies (Spring Boot, Spring Data JPA, Spring Security, Kafka, etc.).

---

## Prerequisites

1. **Java 21** or later.  
2. **Maven 3.8+** (for building the projects).  
3. **PostgreSQL** running locally (default config in `application.properties` for the banking system).  
4. **Kafka** broker running on `localhost:9092` (optional if you’re not testing Kafka features right now).  
5. **Eureka Discovery Server** must start before other microservices.

---

## Getting Started

1. **Clone the Repo**  
   ```bash
   git clone https://github.com/regvedpande/bankingmicroservices.git
   cd bankingmicroservices
   ```
2. **Build All Projects**  
   ```bash
   mvn clean install
   ```
3. **Start Discovery Server**  
   ```bash
   cd discovery-server
   mvn spring-boot:run
   ```
   - Eureka Dashboard: [http://localhost:8761](http://localhost:8761)

4. **Start Banking System**  
   ```bash
   cd ../banking-system
   mvn spring-boot:run
   ```
   - Banking System: [http://localhost:8080](http://localhost:8080)

5. **(Optional) Kafka**  
   - Ensure Kafka is running on `localhost:9092`.  
   - The banking system will publish transaction events to the `transaction-events` topic.

---

## Usage & Endpoints

### 1. Eureka Discovery Server

- **Dashboard:** `http://localhost:8761`  
  - Lists registered services (including `banking-system`).

### 2. Banking System

**Base URL**: `http://localhost:8080`

1. **User Registration**  
   - **Endpoint**: `POST /user/register`  
   - **Payload**:
     ```json
     {
       "username": "john",
       "email": "john@example.com",
       "password": "12345"
     }
     ```  
   - **Response**: 200 OK with user details (password is hashed).

2. **User Login**  
   - **Endpoint**: `POST /user/login`  
   - **Payload**:
     ```json
     {
       "username": "john",
       "password": "12345"
     }
     ```  
   - **Response**: 200 OK with `{"token": "<JWT_TOKEN>"}`

3. **Create a Transaction**  
   - **Endpoint**: `POST /transactions`  
   - **Headers**:  
     - `Authorization: Bearer <JWT_TOKEN>`  
     - `Content-Type: application/json`
   - **Payload**:
     ```json
     {
       "userId": 1,
       "amount": 500.00,
       "type": "DEPOSIT"
     }
     ```  
   - **Response**: 201 Created with the new transaction details.

4. **List All Transactions**  
   - **Endpoint**: `GET /transactions`  
   - **Headers**: `Authorization: Bearer <JWT_TOKEN>`  
   - **Response**: 200 OK with an array of transactions.

---


## Contact

- **GitHub**: [regvedpande](https://github.com/regvedpande)  
- **Email**: regregd@outlook.com

Feel free to reach out if you have any questions or suggestions!

---

**Happy Coding!** If you find this project useful, please consider giving it a star on [GitHub](https://github.com/regvedpande/bankingmicroservices).
