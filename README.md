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


## Step 3: Created Model Layer Entities

In this step, we defined the main entities (models) for the e-commerce project and established relationships between them.

### What we did:
1. Created the following entities:
   - **User**: Represents different roles such as buyers, sellers, admins, and couriers.
   - **About**: Stores detailed information about sellers.
   - **Product**: Represents the products being sold, now includes an `images` field for product visuals.
   - **Category**: Organizes products into different categories.
   - **Order**: Represents orders placed by buyers and includes details such as order date and status.
   - **Cart**: Temporarily holds products for buyers before placing an order.
   - **Payment**: Tracks payment details related to orders.
   - **Review**: Allows buyers to leave feedback on products and sellers.
   - **Shipment**: Manages the shipment process and tracks delivery statuses.
   - **Coupon**: Offers discounts that can be applied to products or orders.
   - **Address**: Stores user address information for orders and shipments.
   - **WishList**: Allows buyers to save products for future purchases.

2. Established relationships between entities:
   - **One-to-One**: For example, between `User` and `About`.
   - **One-to-Many**: Such as between `User` and `Orders`, or `Category` and `Products`.
   - **Many-to-Many**: Between `Product` and `WishList`, as well as `Product` and `Order`.

### Key Relationships:
- **User**: A user can be a buyer, seller, admin, or courier.
- **Product**: A product belongs to a category, can be added to multiple orders, wish lists, and has multiple reviews.
- **Order**: Each order has a buyer and can include multiple products.
- **WishList**: Allows users to store products for future purchase consideration.

This structure establishes a strong foundation for managing the e-commerce application's core data and relationships.
