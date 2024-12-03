# Banking Application

## Overview

This is a RESTful Banking Application built with Spring Boot. It allows users to register, login, and perform various banking operations such as creating accounts and processing transactions.

## Features

- User Registration and Authentication (using JWT)
- Account Management
- Transaction Processing
- REST API Endpoints
- JWT-based authentication

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL (Database)
- Docker (Containerization)
- JUnit and Mockito (Testing)

## Prerequisites

- Java 17
- Maven
- Docker

## Installation

1. Clone the repository:
   ```
   git clone git@github.com:kyawpp/userservice.git
   ```
2. Navigate to the project directory:
   ```
   cd banking
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Running with Docker

To run the application in Docker:

1. Build the Docker image:
   ```
   docker-compose up --build
   ```

## API Endpoints

- `POST /api/users` - Register a new user
- `POST /api/auth/login` - Login and receive JWT tokens
- `POST /api/accounts` - Create a new account (requires authentication)
- `POST /api/transactions` - Create a new transaction (requires authentication)
- `GET /api/transactions` - Get transaction history (requires authentication)

## Testing

Unit and integration tests are written using **JUnit** and **Mockito**. To run the tests, use:

```sh
mvn test
```
