# 📚 Library Management API

A robust, enterprise-grade RESTful API built with Java 17 and Spring Boot 3 for managing library catalogs, user loans,
and inventory. This system implements modern backend architectures, including containerized infrastructure, advanced JPA
dynamic filtering, and a fully automated CI/CD pipeline.

## 🚀 Tech Stack

Core: Java 17, Spring Boot 3.x

Database & Migration: PostgreSQL 15, Flyway

Caching: Redis 7, Spring Cache

Security: Spring Security, JWT (JSON Web Tokens)

Documentation: Springdoc OpenAPI (Swagger)

Infrastructure & CI/CD: Docker, Docker Compose, GitHub Actions

Build Tool: Maven

## ✨ Key Features

Dynamic Search & Filtering: Utilizes custom JPA Specifications and the Criteria API to allow highly flexible, strictly
validated searching across entities without over-engineering or risking SQL injection.

Optimized Pagination: Implements clean REST principles by keeping the JSON payload strictly limited to data arrays,
while injecting pagination metadata (Total Pages, Current Page, Size) directly into HTTP Headers.

Multi-Environment Configuration: Secure "Base + Override" YAML strategies separating local development profiles from
production variables.

Decoupled Infrastructure: Docker Compose handles PostgreSQL, Redis, and Flyway migrations independently of the
application lifecycle, avoiding race conditions.

Automated CI/CD: GitHub Actions automatically builds, packages, and pushes the latest Docker image to the container
registry upon merging to the main branch.

Secured Interactive Documentation: OpenAPI V3 dynamically generates Swagger UI for the development environment, complete
with global JWT authorization capabilities.

## 🛠️ Getting Started (Local Development)

### Prerequisites

* Docker Desktop or Docker Engine
* Java 17 JDK
* Maven

1. Spin up the Infrastructure
   Start the database, run the Flyway migrations, and boot up Redis using the local development override. Note: Docker
   automatically detects compose.override.yml to expose local ports and inject development credentials.

```bash 
   docker compose up -d
```

2. Run the Application
   Start the Spring Boot API using the dev profile to load local database configurations and enable Swagger
   documentation.

```bash 
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Access API Documentation

Once the application is running, the interactive Swagger UI will be available at:
👉 http://localhost:8080/swagger-ui.html

## 🔒 Authentication & Security

This API uses stateless JWT authentication.

Obtain a token via the POST /api/auth/login endpoint.

For subsequent requests, include the token in the HTTP Headers:
Authorization: Bearer <your_token>

When testing locally via Swagger UI, click the green Authorize button at the top of the page and paste your token to
automatically authenticate all requests.

## 🏗️ Architecture & Configuration Strategy

Environment Separation
The application relies on profile-specific properties to guarantee security and performance across environments:

application.yml: Global base settings (e.g., Application Name, default profiles).

application-dev.yml: Local configuration with hardcoded credentials and enabled Swagger UI.

application-prod.yml: Secure configuration relying 100% on injected environment variables. Swagger is strictly disabled
to prevent exposing the API surface.

Database Migrations
Migrations are managed by Flyway but executed via an independent Docker container, not Spring Boot. SQL scripts are
located in src/main/resources/db/migration and are automatically executed against the PostgreSQL database when the
container network starts.

## 🚢 Deployment (CI/CD)

Deployments are fully automated via GitHub Actions.

When code is pushed to the main branch, the .github/workflows/deploy.yml pipeline triggers:

1. Provisions an Ubuntu runner.
2. Sets up JDK 17.
3. Compiles the Maven project.
4. Packages the Java application into a Docker container.
5. Pushes the image to the designated Docker Hub registry as latest.

In the production environment, the compose.prod.yml file is used to securely pull the image, connect it to the internal
Docker network (DB & Redis), and inject production variables without exposing vulnerable ports.


