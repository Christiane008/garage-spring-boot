# Garage — Workshop management with Spring Boot

Web application and REST API for running a car repair garage: customers, vehicles, mechanics, repair jobs (interventions), spare parts and invoices. Closing a repair job automatically generates its invoice, and parts used on a job are deducted from stock.

**Stack:** Java 17 · Spring Boot · Spring Data JPA · Spring Security · Thymeleaf · MySQL · Maven

## Features

- Layered architecture: entities, repositories, services, DTOs and mappers
- Two front doors on the same services: Thymeleaf pages and a JSON REST API under `/api`
- REST resources: `/api/clients`, `/api/vehicules`, `/api/interventions`, `/api/pieces`, `/api/factures`
- Spring Security with three roles: `ADMIN`, `MECANICIEN`, `RECEPTION`
- Domain exceptions (insufficient stock, invalid mileage, forbidden operation) with separate error handlers for the API and the web pages
- Paginated search of repair jobs by status and date range
- Demo data loaded at startup (`DataInitializer`)

## Run it locally

1. Create a MySQL database named `garage_db`.
2. Set your credentials as environment variables (or edit `src/main/resources/application.properties`):
   ```bash
   export DB_USERNAME=root
   export DB_PASSWORD=your-password
   ```
3. Start the app:
   ```bash
   mvn spring-boot:run
   ```
4. Open http://localhost:8081
