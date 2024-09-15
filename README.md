# Project Management Application

This is a Project Management application built using Next.js on the frontend and Spring Boot on the backend. The application provides basic functionalities for managing products and admin sections with different roles and authentication.

### Default Credentials

For easier testing, the following default credentials are provided:

| Username          | Password    | Role          |
| ----------------- | ----------- | ------------- |
| account@email.com | !9Password  | ACCOUNT       |
| admin@email.com   | !91Password | ADMINISTRATOR |

## Features

- User and Admin role-based access.
- Product management.
- Authentication using Spring Boot and basic authentication.
- Role-based restrictions for `/user` and `/admin` paths.

## Technologies Used

### Frontend:

- Next.js
- React
- Fetch API for interacting with the Spring Boot backend

### Backend:

- Spring Boot
- Java
- MySQL (or any relational database)

## Application Structure

### Frontend Routes

1. **Main Page** (`/`)
   - Public login page.
2. **Product Management** (`/product`)
   - Accessible by users with the `ACCOUNT` and `ADMINISTRATOR` role.
   - Lists and manages products.
   - Admin functions like managing user roles, overseeing product data.

## Prerequisites

- Node.js (for Next.js frontend)
- Java JDK 17 (for Spring Boot backend)
- MySQL or PostgreSQL database
- Docker (optional, if you want to run with Docker)

## Konfigurasi

### File `docker-compose.yml`

File `docker-compose.yml` mendefinisikan tiga layanan:

1. **MySQL**:

   - **Gambar**: `mysql:latest`
   - **Port**: `3308:3306`
   - **Variabel Lingkungan**:
     - `MYSQL_ROOT_PASSWORD`: root
     - `MYSQL_DATABASE`: product
     - `MYSQL_USER`: user
     - `MYSQL_PASSWORD`: root

2. **app-endpoint** (Spring Boot):

   - **Port**: `8081:8080`
   - **Lingkungan**:
     - `SPRING_PROFILES_ACTIVE`: prod
     - `SPRING_DATASOURCE_URL`: `jdbc:mysql://mysql:3306/product`
     - `SPRING_DATASOURCE_USERNAME`: user
     - `SPRING_DATASOURCE_PASSWORD`: root

3. **app-display** (Next.js):
   - **Port**: `3000:3000`
   - **Lingkungan**:
     - `NODE_ENV`: production
