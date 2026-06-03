<div align="center">

# 📚 Library Management System API

### REST API for library management — books, members and loans

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)](https://docs.docker.com/compose/)
[![JWT](https://img.shields.io/badge/JWT-Auth-black?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=flat-square&logo=swagger)](https://swagger.io/)
[![Tests](https://img.shields.io/badge/Tests-17%20passing-success?style=flat-square&logo=junit5)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

[English](#english) · [Español](#español)

**Live API:** https://java-library-management-system.onrender.com/swagger-ui.html

</div>

---

## English

### Overview

A RESTful API built with Spring Boot 3 for managing a library system. Handles book registration, member management and loan tracking, secured with JWT authentication and role-based access control.

This project is a migration from a Java console application to a professional REST API, designed as a portfolio project demonstrating real-world backend development practices.

### Features

- 🔐 **JWT Authentication** — stateless auth with access tokens, roles `ADMIN` and `USER`
- 📖 **Book management** — full CRUD with category filtering and availability tracking
- 👤 **Member management** — registration and profile management
- 🔄 **Loan management** — borrow and return books with status tracking
- ✅ **Input validation** — Bean Validation on all request bodies
- 🌐 **Swagger UI** — interactive API documentation with Bearer auth support
- ⚠️ **Global exception handling** — consistent error responses across all endpoints
- 🐘 **PostgreSQL + Docker** — containerized database with pgAdmin
- 🧪 **Unit tests** — JUnit 5 and Mockito, 17 tests passing

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Security | Spring Security 6 + JJWT 0.12.5 |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL 15 |
| Documentation | SpringDoc OpenAPI 3 (Swagger UI) |
| Mapping | MapStruct 1.5.5 |
| Containerization | Docker + Docker Compose |
| Testing | JUnit 5, Mockito |
| Build | Maven 3.9+ |

### Project Structure

```
src/
├── main/
│   └── java/com/librarymanagement/
│       ├── controller/       # REST controllers
│       ├── service/          # Business logic
│       ├── repository/       # Spring Data JPA repositories
│       ├── model/            # JPA entities
│       ├── dto/              # Request and response DTOs
│       ├── enums/            # BookCategory, LoanStatus, Role
│       ├── security/         # JWT filter, SecurityConfig
│       ├── exception/        # GlobalExceptionHandler, custom exceptions
│       └── config/           # OpenAPI, beans
└── test/
    └── java/com/librarymanagement/
        └── service/          # Unit tests
```

### Getting Started

#### Prerequisites

- Java 17+
- Maven 3.9+
- Docker and Docker Compose

#### 1. Clone the repository

```bash
git clone https://github.com/camiladev-cd/java-library-management-system.git
cd java-library-management-system
```

#### 2. Configure environment variables

```bash
cp .env.example .env
# Edit .env with your preferred values — defaults work out of the box
```

#### 3. Start the database

```bash
docker compose up -d
# PostgreSQL → localhost:5432
# pgAdmin    → http://localhost:5050
```

#### 4. Run the application

```bash
mvn spring-boot:run
```

#### 5. Explore the API

Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in your browser.

To authenticate in Swagger UI:
1. Call `POST /api/auth/register` to create an account
2. Call `POST /api/auth/login` to get your JWT
3. Click **Authorize** and paste your token

### API Endpoints

#### Auth — public

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Register a new user |
| `POST` | `/api/auth/login` | Login and receive JWT |

#### Books — requires JWT

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `GET` | `/api/v1/books` | USER, ADMIN | List all books |
| `GET` | `/api/v1/books/{id}` | USER, ADMIN | Get book by ID |
| `GET` | `/api/v1/books/available` | USER, ADMIN | List available books |
| `POST` | `/api/v1/books` | ADMIN | Register a new book |
| `PUT` | `/api/v1/books/{id}` | ADMIN | Update book |
| `DELETE` | `/api/v1/books/{id}` | ADMIN | Delete book |

#### Members — requires JWT

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `GET` | `/api/v1/members` | ADMIN | List all members |
| `GET` | `/api/v1/members/{id}` | USER, ADMIN | Get member by ID |
| `POST` | `/api/v1/members` | ADMIN | Register a member |
| `PUT` | `/api/v1/members/{id}` | ADMIN | Update member |
| `DELETE` | `/api/v1/members/{id}` | ADMIN | Delete member |

#### Loans — requires JWT

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `GET` | `/api/v1/loans` | ADMIN | List all loans |
| `GET` | `/api/v1/loans/{id}` | USER, ADMIN | Get loan by ID |
| `GET` | `/api/v1/loans/member/{id}` | USER, ADMIN | Get loans by member |
| `POST` | `/api/v1/loans` | USER, ADMIN | Create a new loan |
| `PATCH` | `/api/v1/loans/{id}/return` | USER, ADMIN | Return a book |

### Running Tests

```bash
mvn test
```

### Deployment

This API is deployed on [Render](https://render.com).

Live URL: https://java-library-management-system.onrender.com/swagger-ui.html

> **Note:** The free tier on Render spins down after 15 minutes of inactivity. The first request may take 30–60 seconds to respond.

### Environment Variables

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL |
| `SPRING_DATASOURCE_USERNAME` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for signing tokens (min 256-bit) |

---

## Español

### Descripción general

API RESTful construida con Spring Boot 3 para gestionar un sistema de biblioteca. Maneja el registro de libros, gestión de miembros y seguimiento de préstamos, con autenticación JWT y control de acceso basado en roles.

Este proyecto es una migración de una aplicación de consola en Java a una API REST profesional, diseñado como proyecto de portafolio que demuestra prácticas reales de desarrollo backend.

### Funcionalidades

- 🔐 **Autenticación JWT** — auth sin estado con tokens de acceso, roles `ADMIN` y `USER`
- 📖 **Gestión de libros** — CRUD completo con filtrado por categoría y seguimiento de disponibilidad
- 👤 **Gestión de miembros** — registro y administración de perfiles
- 🔄 **Gestión de préstamos** — préstamo y devolución de libros con seguimiento de estado
- ✅ **Validación de entradas** — Bean Validation en todos los request bodies
- 🌐 **Swagger UI** — documentación interactiva con soporte de autenticación Bearer
- ⚠️ **Manejo global de excepciones** — respuestas de error consistentes en todos los endpoints
- 🐘 **PostgreSQL + Docker** — base de datos en contenedor con pgAdmin
- 🧪 **Tests unitarios** — JUnit 5 y Mockito, 17 tests pasando

### Stack tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Seguridad | Spring Security 6 + JJWT 0.12.5 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | PostgreSQL 15 |
| Documentación | SpringDoc OpenAPI 3 (Swagger UI) |
| Mapeo | MapStruct 1.5.5 |
| Contenedores | Docker + Docker Compose |
| Tests | JUnit 5, Mockito |
| Build | Maven 3.9+ |

### Estructura del proyecto

```
src/
├── main/
│   └── java/com/librarymanagement/
│       ├── controller/       # Controladores REST
│       ├── service/          # Lógica de negocio
│       ├── repository/       # Repositorios Spring Data JPA
│       ├── model/            # Entidades JPA
│       ├── dto/              # DTOs de request y response
│       ├── enums/            # BookCategory, LoanStatus, Role
│       ├── security/         # Filtro JWT, SecurityConfig
│       ├── exception/        # GlobalExceptionHandler, excepciones custom
│       └── config/           # OpenAPI, beans
└── test/
    └── java/com/librarymanagement/
        └── service/          # Tests unitarios
```

### Inicio rápido

#### Prerrequisitos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose

#### 1. Clonar el repositorio

```bash
git clone https://github.com/camiladev-cd/java-library-management-system.git
cd java-library-management-system
```

#### 2. Configurar variables de entorno

```bash
cp .env.example .env
# Edita .env con tus valores — los valores por defecto funcionan directamente
```

#### 3. Levantar la base de datos

```bash
docker compose up -d
# PostgreSQL → localhost:5432
# pgAdmin    → http://localhost:5050
```

#### 4. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

#### 5. Explorar la API

Abre [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) en tu navegador.

Para autenticarte en Swagger UI:
1. Llama a `POST /api/auth/register` para crear una cuenta
2. Llama a `POST /api/auth/login` para obtener tu JWT
3. Haz clic en **Authorize** y pega tu token

### Endpoints de la API

#### Auth — público

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/auth/register` | Registrar nuevo usuario |
| `POST` | `/api/auth/login` | Iniciar sesión y recibir JWT |

#### Libros — requiere JWT

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/api/v1/books` | USER, ADMIN | Listar todos los libros |
| `GET` | `/api/v1/books/{id}` | USER, ADMIN | Obtener libro por ID |
| `GET` | `/api/v1/books/available` | USER, ADMIN | Listar libros disponibles |
| `POST` | `/api/v1/books` | ADMIN | Registrar un nuevo libro |
| `PUT` | `/api/v1/books/{id}` | ADMIN | Actualizar libro |
| `DELETE` | `/api/v1/books/{id}` | ADMIN | Eliminar libro |

#### Miembros — requiere JWT

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/api/v1/members` | ADMIN | Listar todos los miembros |
| `GET` | `/api/v1/members/{id}` | USER, ADMIN | Obtener miembro por ID |
| `POST` | `/api/v1/members` | ADMIN | Registrar un miembro |
| `PUT` | `/api/v1/members/{id}` | ADMIN | Actualizar miembro |
| `DELETE` | `/api/v1/members/{id}` | ADMIN | Eliminar miembro |

#### Préstamos — requiere JWT

| Método | Endpoint | Rol | Descripción |
|---|---|---|---|
| `GET` | `/api/v1/loans` | ADMIN | Listar todos los préstamos |
| `GET` | `/api/v1/loans/{id}` | USER, ADMIN | Obtener préstamo por ID |
| `GET` | `/api/v1/loans/member/{id}` | USER, ADMIN | Obtener préstamos por miembro |
| `POST` | `/api/v1/loans` | USER, ADMIN | Crear un nuevo préstamo |
| `PATCH` | `/api/v1/loans/{id}/return` | USER, ADMIN | Devolver un libro |

### Ejecutar tests

```bash
mvn test
```

### Deploy

Esta API está desplegada en [Render](https://render.com).

URL en producción: https://java-library-management-system.onrender.com/swagger-ui.html

> **Nota:** El tier gratuito de Render se apaga tras 15 minutos de inactividad. La primera petición puede tardar 30–60 segundos en responder.

### Variables de entorno

| Variable | Descripción |
|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos |
| `JWT_SECRET` | Clave secreta para firmar tokens (mín. 256-bit) |

---

<div align="center">

Made with ☕ by [Sergio Silva](https://github.com/camiladev-cd)

</div>