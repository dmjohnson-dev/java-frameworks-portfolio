# D287 – Java Frameworks (Spring Boot) | Portfolio Project

This project is a full-stack Java web application built using Spring Boot and modern Java frameworks. It demonstrates MVC architecture, server-side rendering, database persistence, and real-world application logic. This repository is published as a **portfolio version** of an academic project completed as part of Western Governors University.

---

## What This Project Demonstrates

- Spring Boot application configuration and lifecycle
- MVC architecture using Spring Controllers
- Server-side rendering with Thymeleaf
- Database persistence with Spring Data JPA and Hibernate
- Input validation and business logic enforcement
- Inventory and state-based UI behavior
- Clean separation of concerns (Controller / Service / Repository)
- Git branching, merging, and version control best practices

---

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA (Hibernate)**
- **Thymeleaf**
- **H2 Database**
- **Maven**
- **Git / GitHub**

---

## Application Overview

The application simulates a small e-commerce / inventory management system:

- Products are displayed dynamically from the database
- Inventory levels are enforced at the service layer
- User actions trigger success and error flash messages
- UI state updates based on business rules (e.g., inventory availability)

---

## Project Structure
src/
├─ main/
│ ├─ java/ # Controllers, services, repositories, entities
│ └─ resources/ # application.properties, templates, static assets
└─ test/ # Application and integration tests
pom.xml # Maven configuration
---

## How to Run Locally

### Prerequisites
- Java 17+ (or compatible version)
- Maven (or Maven Wrapper)

### Run the Application

**Windows:**
```bash
mvnw.cmd spring-boot:run

