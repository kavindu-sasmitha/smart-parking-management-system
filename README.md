# Smart Parking Management System (SPMS)

A cloud-native, microservice-based platform for real-time parking space management, built for **ITS 1018 – Software Architectures & Design Patterns II** (Graduate Diploma in Software Engineering, IJSE).

SPMS allows drivers to search and reserve parking spaces in real time, lets parking owners manage their spaces dynamically, simulates vehicle entry/exit, and handles mock payments with digital receipt generation.

## Architecture

```
                        ┌──────────────────┐
                        │   Eureka Server    │  (Service Registry - 8761)
                        └─────────▲──────────┘
                                  │ registers
        ┌─────────────┬──────────┼──────────┬─────────────┐
        │              │          │           │             │
  ┌─────▼─────┐  ┌─────▼─────┐ ┌─▼───────┐ ┌─▼──────────┐ ┌▼────────────┐
  │User Service│  │Vehicle Svc│ │Parking  │ │Payment Svc │ │ Config      │
  │   :8081    │  │  :8082    │ │Service  │ │   :8084    │ │ Server:8888 │
  └────────────┘  └───────────┘ │ :8083   │ └────────────┘ └─────────────┘
        ▲               ▲       └────▲────┘        ▲
        │               │            │              │
        └───────────────┴────────────┴──────────────┘
                          │
                  ┌───────▼────────┐
                  │  API Gateway     │  :8080  (single entry point)
                  └─────────────────┘
                          ▲
                          │
                     Postman / Client
```

## Tech Stack

| Component | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Microservices | Spring Cloud 2023.0.1 |
| Service Registry | Spring Cloud Netflix Eureka |
| Config Management | Spring Cloud Config Server |
| API Gateway | Spring Cloud Gateway |
| Data Storage | In-memory (per-service, non-persistent) |
| API Testing | Postman |
| Build Tool | Maven |

## Services

| Service | Port | Base Path (via Gateway) | Responsibility |
|---|---|---|---|
| Eureka Server | 8761 | — | Service discovery/registration |
| Config Server | 8888 | — | Centralized configuration |
| API Gateway | 8080 | — | Single entry point, routes to services |
| User Service | 8081 | `/api/users` | Registration, login, profile, booking history |
| Vehicle Service | 8082 | `/api/vehicles` | Vehicle registration, entry/exit simulation, logs |
| Parking Service | 8083 | `/api/parking` | Space CRUD, search, reserve/release, status updates |
| Payment Service | 8084 | `/api/payments` | Mock payment processing, receipts |

## Project Structure

```
SPMS/
├── eureka-server/
├── config-server/
├── api-gateway/
├── user-service/
├── vehicle-service/
├── parking-service/
├── payment-service/
├── docs/
│   └── screenshots/
│       └── eureka_dashboard.png
├── postman_collection.json
└── README.md
```

## Running the Project

Start the services in this order (each has its own `mvnw`/Maven build):

1. **Eureka Server** – `cd eureka-server && ./mvnw spring-boot:run` → http://localhost:8761
2. **Config Server** – `cd config-server && ./mvnw spring-boot:run` → http://localhost:8888
3. **User / Vehicle / Parking / Payment Services** – run each with `./mvnw spring-boot:run` (any order, after Eureka + Config are up)
4. **API Gateway** – `cd api-gateway && ./mvnw spring-boot:run` → http://localhost:8080

Once all services show **UP** on the Eureka dashboard (`localhost:8761`), all API calls can be made through the Gateway at `http://localhost:8080`.

## API Overview (via Gateway – localhost:8080)

**User Service** — `/api/users`
- `POST /register` – register a user (role: `DRIVER`, `OWNER`, `ADMIN`)
- `POST /login` – authenticate
- `GET /{id}` – get profile
- `PUT /{id}` – update profile
- `GET /{id}/bookings` – booking history

**Vehicle Service** — `/api/vehicles`
- `POST /` – register vehicle
- `GET /?userId=` – list vehicles (optionally by user)
- `POST /{id}/entry` – simulate entry
- `POST /{id}/exit` – simulate exit
- `GET /{id}/logs` – entry/exit logs

**Parking Service** — `/api/parking`
- `POST /` – create space
- `GET /?city=&zone=&status=` – search spaces
- `PUT /{id}/reserve` / `PUT /{id}/release`
- `PATCH /{id}/status` – update status (`AVAILABLE`, `OCCUPIED`, `RESERVED`, `MAINTENANCE`)

**Payment Service** — `/api/payments`
- `POST /` – process mock payment
- `GET /user/{userId}` – payment history
- `GET /receipt/{receiptId}` – digital receipt

Full request/response examples are in the Postman collection below.

## Resources

- [Postman Collection](Postman-Collection/postman_collection.json)
- [Screenshot](Screenshot)
## Testing

All endpoints were tested using the Postman collection above, covering:
- Successful CRUD flows for each service
- Error handling (invalid IDs, missing required fields)
- End-to-end flow: register user → register vehicle → create parking space → reserve → simulate entry → process payment → get receipt → release space → simulate exit

## Author

**Kavindu Sasmitha (Saka)**
Graduate Diploma in Software Engineering – IJSE
ITS 1018 – Software Architectures & Design Patterns II