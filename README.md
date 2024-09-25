# e-commerce-expert-backend

This project is the backend for an e-commerce application, providing APIs for managing products, brands, and categories.

## Step 1: Project Configuration and Dependency Management

In this step, we configured the project dependencies and set up database connections.

### What we did:
1. Updated `pom.xml` to include the following dependencies:
    - **Spring Boot Web**: To build RESTful web services.
    - **Spring Data JPA**: For database interactions.
    - **Spring Boot Starter Validation**: For DTO validation.
    - **Spring Boot DevTools**: To speed up the development process.
    - **MySQL and PostgreSQL**: To support both SQL databases.
    - **MongoDB**: For NoSQL database support.
    - **Bucket4J**: To implement rate limiting.
    - **Apache Commons Text**: For Levenshtein Distance algorithm.
    - **Commons Codec**: For the Soundex algorithm.
    - **Springdoc OpenAPI**: To integrate Swagger for API documentation.
    - **Lombok**: To reduce boilerplate code for getters, setters, and constructors.

2. Configured the `application.properties` file:
    - Set up MySQL as the primary database.
    - Defined the server port as `8090`.
    - Configured OpenAPI Swagger integration.
    - Left placeholders for PostgreSQL, MongoDB, and H2 databases for future configuration.

### Dependencies Used:
- **Spring Boot Starter Web**: For building web applications and RESTful APIs.
- **Spring Boot Starter JPA**: For simplifying database interactions.
- **MySQL/PostgreSQL/MongoDB**: To provide database management.
- **Springdoc OpenAPI**: For automatic generation of Swagger documentation.
- **Bucket4J**: For implementing rate limiting mechanisms.
- **Lombok**: For reducing repetitive code in entity and DTO classes.

## Step 2: Create Layered Architecture Folder Structure

We created a folder structure that follows a layered architecture, dividing the project into the following layers:

### What we did:
1. Created the following folders:
   - `controller`: For API endpoints (REST Controllers).
   - `service`: For business logic and services. Created `impl` subdirectory for implementations.
   - `repository`: For data access logic (Database repositories).
   - `model`: For database entity classes.
   - `dto`: For Data Transfer Objects (DTOs).
   - `mapper`: For mapping between entities and DTOs.
   - `exception`: For custom exception handling.
   - `config`: For configuration classes (e.g., security or Swagger configuration).
   - `util`: For utility classes and helper functions.

### Benefits of Layered Architecture:
- **Modularity**: Each layer has a distinct responsibility, making the project more maintainable and easier to scale.
- **Separation of Concerns**: Isolates business logic, data access, and API endpoints, making the code more organized and testable.
- **Reusability**: Components can be reused across different layers, reducing code duplication.