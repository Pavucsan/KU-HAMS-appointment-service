# Appointment Service

This microservice manages patient appointments, including booking, retrieval, and history. It integrates with authentication for secure access and supports CORS for front-end clients.

## Table of Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Setup & Configuration](#setup--configuration)
- [Running the Service](#running-the-service)
- [Environment Variables](#environment-variables)
- [API Endpoints](#api-endpoints)
- [Data Model](#data-model)
- [Security & CORS](#security--cors)
- [Testing](#testing)
- [Future Improvements](#future-improvements)

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL database

## Setup & Configuration

1. **Clone repository**
   ```bash
   git clone https://github.com/your-org/appointment-service.git
   cd appointment-service
   ```
2. **Configure** database and JWT settings in `application.yml` or via environment variables (see below).
3. **Build**
   ```bash
   mvn clean install
   ```

## Running the Service

```bash
mvn spring-boot:run
```
The service starts on port `8082` by default.

## Environment Variables

| Variable                     | Description                             | Default                                         |
|------------------------------|-----------------------------------------|-------------------------------------------------|
| `SPRING_DATASOURCE_URL`      | JDBC URL for PostgreSQL                 | `jdbc:postgresql://localhost:5432/appointmentdb` |
| `SPRING_DATASOURCE_USERNAME` | DB username                             | `postgres`                                      |
| `SPRING_DATASOURCE_PASSWORD` | DB password                             | `password`                                      |
| `JWT_SECRET`                 | Secret key for signing JWT              | (required)                                      |
| `JWT_EXPIRATION_MS`          | Token expiration in milliseconds        | `3600000` (1 hr)                                |

## API Endpoints

### Booking & Retrieval (Protected)

All endpoints below require `Authorization: Bearer <token>`.

| Method | URI                                   | Request Body                                           | Response                                      | Description                              |
|--------|---------------------------------------|--------------------------------------------------------|-----------------------------------------------|------------------------------------------|
| POST   | `/api/admin/book`                     | `{ patientId, doctorId, dateTime }`                    | `{ status,message,data:Appointment }`          | Book a new appointment                   |
| GET    | `/api/admin/appointments`             | —                                                      | `List<Appointment>`                            | List all appointments (admin)            |
| GET    | `/api/admin/patient/{patientId}`      | —                                                      | `List<Appointment>`                            | Get appointments for a specific patient  |
| GET    | `/api/admin/doctor/{doctorId}`        | —                                                      | `List<Appointment>`                            | Get appointments for a specific doctor   |

### Public Retrieval

| Method | URI                                   | Response                                      | Description                              |
|--------|---------------------------------------|-----------------------------------------------|------------------------------------------|
| GET    | `/api/appointments/history`           | `List<Appointment>`                            | Get logged-in patient’s appointment history |

## Data Model

- **Appointment**
    - `id` (Long)
    - `patient` (Patient entity)
    - `doctor` (Doctor entity)
    - `appointmentDateTime` (LocalDateTime)
    - `status` (Enum: SCHEDULED, COMPLETED, CANCELLED)
    - `createdAt`, `updatedAt` (timestamps)

## Security & CORS

- **JWT** authentication.
- **Roles**: `PATIENT`, `DOCTOR`, `ADMIN`.
- **CORS** configured to allow origins `http://localhost:3000`, `http://localhost:3001` with methods GET, POST, PUT, DELETE, OPTIONS, and headers `Authorization`, `Content-Type`.

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:3001"));
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization","Content-Type"));
    config.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**",config);
    return source;
}
```

## Testing

- **Unit Tests**: `mvn test`
- **Integration Tests**: Configure test database or use Dockerized PostgreSQL.

## Future Improvements

- Add appointment cancellation & rescheduling endpoints.
- Email/SMS notifications on booking.
- Pagination and filtering for appointment lists.
- Improved metrics & health checks with Spring Actuator.
- Dockerfile and Docker Compose for full-stack local development.

---
*Generated on 2025-04-28*

