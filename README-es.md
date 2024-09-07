# Microservicio de Gateway de Usuarios

Este proyecto es un microservicio de **autenticación y registro de usuarios** que utiliza **Programación Reactiva** con **Spring WebFlux**, **MongoDB**, y **JWT** (JSON Web Token) para la seguridad. El servicio proporciona endpoints para registrar nuevos usuarios, iniciar sesión y gestionar sesiones de usuarios.

## Tabla de Contenidos
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración](#configuración)
- [Endpoints de la API](#endpoints-de-la-api)
- [Casos de Uso](#casos-de-uso)
- [Seguridad](#seguridad)

## Tecnologías

- **Java 17**
- **Spring WebFlux**
- **MongoDB Reactivo**
- **Spring Security**
- **JWT (JSON Web Token)**
- **BCrypt para el hash de contraseñas**
- **Maven** (para la gestión de dependencias)

## Estructura del Proyecto

El proyecto sigue los principios de **arquitectura limpia**, separando la lógica del dominio de la infraestructura. A continuación, una visión general de la estructura:

### Capa de Dominio
- `domain.model`: Contiene la lógica de negocio central y los modelos (`User`, `DTOs` como `SignUpDTO`, `LogInDTO`, etc.).
- `domain.usecase`: Contiene los casos de uso de negocio (`SignUpUseCase`, `LogInUseCase`).
- `domain.model.user.gateway`: La interfaz gateway para las operaciones de persistencia de usuarios.

### Capa de Infraestructura
- `infrastructure.drivenadapter`: Contiene los adaptadores para interactuar con MongoDB (`UserMongoAdapter`) y las utilidades de seguridad (`JwtProvider`).
- `infrastructure.security`: Configuración de la seguridad basada en JWT, filtros y autorización (`JwtFilter`, `SecurityConfig`).
- `infrastructure.entrypoint`: Puntos de entrada API para manejar las solicitudes de autenticación usando rutas reactivas (`AuthHandler`, `RouterAuth`).

## Configuración

### Prerrequisitos
- **Java 17** o superior
- Instancia de **MongoDB** en ejecución
- **Maven**

### Pasos para Ejecutar Localmente

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-repo/user-gateway.git
   ```

2. Navega al directorio del proyecto:
   ```bash
   cd user-gateway
   ```

3. Configura las propiedades de la aplicación:
   Asegúrate de que el archivo `application.yml` esté correctamente configurado con tu URI de MongoDB, el secreto JWT y otros valores de configuración.

4. Construye el proyecto:
   ```bash
   mvn clean install
   ```

5. Ejecuta el proyecto:
   ```bash
   mvn spring-boot:run
   ```

6. El servicio estará accesible en `http://localhost:8080`.

## Endpoints de la API

### Autenticación

#### Registro de Usuario
- **Endpoint**: `POST /auth/signup`
- **Cuerpo de la Solicitud**:
  ```json
  {
      "name": "John",
      "lastName": "Doe",
      "email": "johndoe@example.com",
      "password": "password123"
  }
  ```
- **Respuesta**: El objeto del usuario registrado en formato JSON.

#### Inicio de Sesión
- **Endpoint**: `POST /auth/login`
- **Cuerpo de la Solicitud**:
  ```json
  {
      "email": "johndoe@example.com",
      "password": "password123"
  }
  ```
- **Respuesta**: Un token JWT si la autenticación es exitosa.

### Endpoint Protegido

#### Hello
- **Endpoint**: `GET /hello`
- **Autorización**: Requiere un token JWT válido con el rol `ADMIN`.

## Casos de Uso

### SignUpUseCase

Este caso de uso maneja el registro de usuarios. Interactúa con el `UserGateway` para persistir al nuevo usuario en MongoDB y codificar su contraseña utilizando `BCrypt`.

```java
public Mono<User> signUp(SignUpDTO dto) {
    return userGateway.signUp(dto);
}
```

### LogInUseCase

Este caso de uso maneja el inicio de sesión. Valida las credenciales del usuario y devuelve un token JWT si es exitoso.

```java
public Mono<TokenDTO> login(LogInDTO dto) {
    return userGateway.login(dto);
}
```

## Seguridad

### Autenticación JWT

La configuración de seguridad utiliza tokens JWT para proteger las rutas. El `JwtFilter` extrae y verifica el token JWT de los encabezados de las solicitudes entrantes, permitiendo el acceso a rutas protegidas solo si el token es válido.

- **Cifrado de Contraseña**: Las contraseñas se cifran utilizando `BCrypt` antes de ser almacenadas en MongoDB.
- **Autorización**: Se utilizan roles (como `ADMIN`) para proteger los endpoints.

La configuración de seguridad (`SecurityConfig`) define la cadena de filtros de seguridad, permitiendo acceso público a las rutas de autenticación (`/auth/**`) y protegiendo las demás rutas.

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

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.