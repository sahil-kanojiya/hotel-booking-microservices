# Hotel Booking Microservices Ecosystem

This is a comprehensive Spring Boot microservices project demonstrating a distributed system architecture. The project
consists of several independent microservices communicating seamlessly, utilizing modern Spring Cloud and Spring Boot
capabilities.

## Architecture & Services

The system is broken down into the following core microservices:

- **api-gateway**: The entry point to the system, acting as a reverse proxy, routing requests to appropriate downstream
  services, and handling cross-cutting concerns like security and rate limiting.
- **service-registry**: A Netflix Eureka server used for service discovery. All other microservices register themselves
  here, allowing them to dynamically find and communicate with one another without hardcoded URLs.
- **config-server**: A centralized Spring Cloud Config Server that provides externalized configuration for all
  microservices in the distributed system.
- **user-service**: Manages user profiles, information, and handles the composition of user data alongside their hotel
  ratings.
- **hotel-service**: Manages hotel entities, availability, and general hotel data.
- **rating-service**: Handles user reviews and ratings for hotels.

## Technologies Used

- Java 22
- Spring Boot 3.x
- Spring Cloud (Eureka, API Gateway, Config Server)
- Netflix Eureka (Service Discovery)
- Resilience4j (Circuit Breaker, Rate Limiting, Retry mechanisms)
- Okta (OAuth2 / OIDC Security)
- MySQL / Spring Data JPA
- Maven (Build Tool)
- Lombok

## Setup and Running Locally

### Prerequisites

- JDK 22
- MySQL Server
- Maven

### Environment Variables

Before running the services, ensure you export the necessary environment variables for the database and OAuth2
configurations to avoid committing secrets:

- `DB_PASSWORD` (Your MySQL root password)
- `OKTA_CLIENT_ID` (Your Okta application client ID)
- `OKTA_CLIENT_SECRET` (Your Okta application client secret)

### Bootstrapping the Services

To start the entire ecosystem, navigate to each service directory and run:

```bash
./mvnw spring-boot:run
```

**Recommended Startup Order:**

1. `service-registry`
2. `config-server`
3. `api-gateway`
4. `user-service`, `hotel-service`, `rating-service`

## Configuration

Demo configurations are provided as `application.yml.demo` and `application.properties.demo` in each service's
`src/main/resources` folder. The actual `.yml` and `.properties` files are sanitized and rely on environment variables
for secrets.
