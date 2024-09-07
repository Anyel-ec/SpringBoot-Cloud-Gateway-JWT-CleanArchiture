# User Gateway Microservice

This project is a **user authentication and registration** microservice using **Reactive Programming** with **Spring WebFlux**, **MongoDB**, and **JWT** (JSON Web Token) for security. The service provides endpoints for signing up new users, logging in, and managing user sessions.

## Table of Contents
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [Use Cases](#use-cases)
- [Security](#security)

## Technologies

- **Java 17**
- **Spring WebFlux**
- **Reactive MongoDB**
- **Spring Security**
- **JWT (JSON Web Token)**
- **BCrypt for password hashing**
- **Maven** (for dependency management)

## Project Structure

The project follows **clean architecture** principles, separating the domain logic from the infrastructure. Below is a brief overview of the structure:

### Domain Layer
- `domain.model`: Contains the core business logic and models (`User`, `DTOs` like `SignUpDTO`, `LogInDTO`, etc.).
- `domain.usecase`: Contains the business use cases (`SignUpUseCase`, `LogInUseCase`).
- `domain.model.user.gateway`: The gateway interface for user persistence operations.

### Infrastructure Layer
- `infrastructure.drivenadapter`: Contains adapters to interact with MongoDB (`UserMongoAdapter`) and security utilities (`JwtProvider`).
- `infrastructure.security`: Configuration for JWT-based security, filters, and authorization (`JwtFilter`, `SecurityConfig`).
- `infrastructure.entrypoint`: API entry points for handling authentication requests using reactive web routes (`AuthHandler`, `RouterAuth`).

## Setup

### Prerequisites
- **Java 17** or higher
- **MongoDB** instance running
- **Maven**

### Steps to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/user-gateway.git
   ```

2. Navigate into the project directory:
   ```bash
   cd user-gateway
   ```

3. Configure the application properties:
   Ensure that the `application.yml` is correctly set up with your MongoDB URI, JWT secret, and other configuration values.

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the project:
   ```bash
   mvn spring-boot:run
   ```

6. The service will be accessible on `http://localhost:8080`.

## API Endpoints

### Authentication

#### Sign Up
- **Endpoint**: `POST /auth/signup`
- **Request Body**:
  ```json
  {
      "name": "John",
      "lastName": "Doe",
      "email": "johndoe@example.com",
      "password": "password123"
  }
  ```
- **Response**: The signed-up user object in JSON format.

#### Log In
- **Endpoint**: `POST /auth/login`
- **Request Body**:
  ```json
  {
      "email": "johndoe@example.com",
      "password": "password123"
  }
  ```
- **Response**: A JWT token if authentication is successful.

### Secured Endpoint

#### Hello
- **Endpoint**: `GET /hello`
- **Authorization**: Requires a valid JWT token with `ADMIN` role.

## Use Cases

### SignUpUseCase

This use case handles user registration. It interacts with the `UserGateway` to persist the new user to MongoDB and encode the user's password using `BCrypt`.

```java
public Mono<User> signUp(SignUpDTO dto) {
    return userGateway.signUp(dto);
}
```

### LogInUseCase

This use case handles user login. It validates user credentials and returns a JWT token if successful.

```java
public Mono<TokenDTO> login(LogInDTO dto) {
    return userGateway.login(dto);
}
```

## Security

### JWT Authentication

The security configuration leverages JWT tokens to secure routes. The `JwtFilter` extracts and verifies the JWT token from the incoming requests' headers, allowing access to protected routes only if the token is valid.

- **Password Encryption**: Passwords are hashed using `BCrypt` before being stored in MongoDB.
- **Authorization**: Uses roles (`ADMIN`) for securing endpoints.

The `SecurityConfig` defines the security filter chain, allowing public access to authentication routes (`/auth/**`) while protecting other routes.

```java
@Bean
public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
    return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchangeSpec -> exchangeSpec
                    .pathMatchers("/auth/**").permitAll()
                    .anyExchange().authenticated())
            .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(securityContextRepository)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .logout(ServerHttpSecurity.LogoutSpec::disable)
            .build();
}
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.
