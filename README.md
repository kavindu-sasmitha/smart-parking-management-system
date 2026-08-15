# Smart Parking Management System (SPMS)

A cloud-native, microservice-based backend platform for managing urban parking spaces in real time — built for **ITS 1018 – Software Architectures & Design Patterns II** (Graduate Diploma in Software Engineering, IJSE).

## Resources
- [Postman Collection](./postman_collection.json)
- ![Eureka Dashboard](./docs/screenshots/eureka_dashboard.png)

## Business Scenario

SPMS solves urban parking congestion by giving drivers real-time visibility into available parking, letting owners manage their spaces dynamically, and handling digital payments — all through a decoupled, scalable microservice architecture.

## Architecture Overview

| Component | Port | Role |
|---|---|---|
| **eureka-server** | 8761 | Service Registry & Discovery (Netflix Eureka) |
| **config-server** | 8888 | Centralized configuration management (Spring Cloud Config, native profile) |
| **api-gateway** | 8080 | Single entry point routing to all microservices (Spring Cloud Gateway) |
| **user-service** | 8081 | Registers/authenticates users & owners, booking history |
| **vehicle-service** | 8082 | Manages vehicles, simulates entry/exit tracking |
| **parking-service** | 8083 | Manages parking spaces, availability, reservations |
| **payment-service** | 8084 | Mock payment processing & digital receipt generation |

```
Client (Postman)
      │
      ▼
 API Gateway (8080)
      │
      ├──► User Service (8081)      ┐
      ├──► Vehicle Service (8082)   ├─ all register with ─► Eureka Server (8761)
      ├──► Parking Service (8083)   │        and fetch config from
      └──► Payment Service (8084)   ┘        Config Server (8888)
```

All business microservices are built with **Spring Boot 3.2.5** (Java 17) and use **in-memory storage** (thread-safe `ConcurrentHashMap` repositories) so the whole system runs standalone with zero database setup — matching the assignment's "in-memory or persistent" requirement for logs/history.

## Tech Stack
- Java 17, Spring Boot 3.2.5
- Spring Cloud 2023.0.1 (Netflix Eureka, Config Server, Gateway)
- Spring Web, Spring Validation
- Maven

## Project Structure
```
SPMS/
├── eureka-server/       # Service registry
├── config-server/       # Centralized config (native profile)
├── api-gateway/         # Single entry point / routing
├── user-service/        # Users & owners
├── vehicle-service/     # Vehicles & entry/exit simulation
├── parking-service/     # Parking spaces & reservations
├── payment-service/     # Mock payments & receipts
├── docs/screenshots/    # Eureka dashboard screenshot goes here
├── postman_collection.json
└── README.md
```

## How to Run

Run each module in its **own terminal**, in this order (each is a standalone Maven project):

```bash
# 1. Start Eureka Server first
cd eureka-server && mvn spring-boot:run

# 2. Start Config Server
cd config-server && mvn spring-boot:run

# 3. Start the business microservices (any order, after step 1 & 2)
cd user-service && mvn spring-boot:run
cd vehicle-service && mvn spring-boot:run
cd parking-service && mvn spring-boot:run
cd payment-service && mvn spring-boot:run

# 4. Start the API Gateway last
cd api-gateway && mvn spring-boot:run
```

Once everything is up:
- Eureka Dashboard → http://localhost:8761 (all 5 services + gateway should appear as `UP`)
- All requests go through the gateway → http://localhost:8080

> Config Server integration uses `optional:configserver:` — so if you skip starting the config server, every service still boots fine using its own local `application.yml` defaults.

## API Endpoints (via Gateway — http://localhost:8080)

### User Service — `/api/users`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a new user/owner |
| POST | `/api/users/login` | Authenticate user |
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get user by id |
| PUT | `/api/users/{id}` | Update user profile |
| DELETE | `/api/users/{id}` | Delete user |
| GET | `/api/users/{id}/bookings` | Booking/activity history |

### Vehicle Service — `/api/vehicles`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/vehicles` | Register a vehicle |
| GET | `/api/vehicles` | List all (or `?userId=`) |
| GET | `/api/vehicles/{id}` | Get vehicle by id |
| PUT | `/api/vehicles/{id}` | Update vehicle |
| DELETE | `/api/vehicles/{id}` | Remove vehicle |
| POST | `/api/vehicles/{id}/entry` | Simulate vehicle entry |
| POST | `/api/vehicles/{id}/exit` | Simulate vehicle exit |
| GET | `/api/vehicles/{id}/logs` | Entry/exit history |

### Parking Space Service — `/api/parking`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/parking` | Create a parking space |
| GET | `/api/parking?city=&zone=&status=` | Search/filter spaces |
| GET | `/api/parking/{id}` | Get space by id |
| GET | `/api/parking/owner/{ownerId}` | Spaces by owner |
| PUT | `/api/parking/{id}` | Update space details |
| DELETE | `/api/parking/{id}` | Remove space |
| PUT | `/api/parking/{id}/reserve` | Reserve a space |
| PUT | `/api/parking/{id}/release` | Release a space |
| PATCH | `/api/parking/{id}/status` | Manual/simulated IoT status update |

### Payment Service — `/api/payments`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/payments` | Process a mock payment & generate receipt |
| GET | `/api/payments` | List all payments |
| GET | `/api/payments/{id}` | Get payment by id |
| GET | `/api/payments/user/{userId}` | Payments by user |
| GET | `/api/payments/receipt/{receiptId}` | Fetch digital receipt |

## Testing

All endpoints were tested with **Postman** — see [`postman_collection.json`](./postman_collection.json). Each service also exposes a `GET /.../health` endpoint for a quick liveness check. Error handling (404 not found, 400 validation errors, 409 conflicts such as double-reservation) is implemented via `@RestControllerAdvice` global exception handlers in every service.

## Notes for Submission
1. Take a screenshot of the Eureka dashboard (http://localhost:8761) once all services are registered and save it as `docs/screenshots/eureka_dashboard.png`.
2. Export/verify `postman_collection.json` at the project root (already included here).
3. Push this whole folder to a GitHub repository with this README at the root.
