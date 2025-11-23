# Logistics Tracking System (17TRACK Integration)

This project provides an MVP for multi-store parcel tracking that integrates with the 17TRACK API. It includes a Spring Boot + MyBatis backend with JWT login and a Vue 3 + Vite frontend for login, platform credential configuration, and tracking management.

## Project Structure
- `backend/`: Spring Boot service with MySQL persistence scaffolding, Redis cache, RabbitMQ messaging, and JWT-based auth stubs.
- `frontend/`: Vue 3 + Vite SPA with login, platform credential setup, and basic tracking list/import views.
- `docs/`: PRD, functional spec, user stories, and API design supporting the MVP scope.

## Prerequisites
- JDK 17+
- Maven 3.9+
- Node.js 18+ and npm
- MySQL, Redis, RabbitMQ (local defaults are defined in `backend/src/main/resources/application.yml`)

## Backend Setup
1. Update `backend/src/main/resources/application.yml` with your database, Redis, RabbitMQ, and JWT admin credentials.
2. From `backend/`, install dependencies and start the service:
   ```bash
   mvn spring-boot:run
   ```
3. Default admin login uses the values under `security.admin` in `application.yml`.

## Frontend Setup
1. From `frontend/`, install dependencies:
   ```bash
   npm install
   ```
2. Start the dev server:
   ```bash
   npm run dev
   ```
3. The SPA includes pages for login, platform credential configuration, and a tracking list with import actions.

## Available Documentation
Refer to the `docs/` folder for detailed requirements, design, and API contracts:
- `docs/PRD.md`
- `docs/FunctionalSpec.md`
- `docs/UserStories.md`
- `docs/API.md`

## Notes
- MyBatis mapper XML files are not yet included; add them under `backend/src/main/resources/mapper/` as the data layer is implemented.
- Replace placeholder secrets in `application.yml` before deploying.
