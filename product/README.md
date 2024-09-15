# Product Management - Endpoint Base
## Project Overview
This project is a backend API built using Spring Boot version 3.3 and Java 17. It includes:

* **JWT Authentication** for secure user authorization.
* **CORS management** for integration with a Next.js frontend application.
* **Dockerized** setup for easy deployment with PostgreSQL as the database.
* **Spring Boot** serves as the backend for managing products, users, and authentication with JWT, while Next.js handles the frontend.

## Key Features
**1. JWT Authentication:**
* Secure endpoints with token-based authentication.
* Protect APIs and allow only authorized access to resources.

**2. CORS Configuration:**
* Allow cross-origin requests from a Next.js frontend hosted at http://localhost:3000.
* Flexible CORS setup that can be modified for production environments.

**3.Docker Integration:**
* Run the entire project (backend and database) in isolated Docker containers.
* Pre-configured docker-compose to easily spin up PostgreSQL and the Spring Boot application.

**4.PostgreSQL Database:**
* A PostgreSQL instance is set up via Docker Compose.
* Easily configurable for production-grade deployment.