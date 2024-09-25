# e-commerce-expert-backend

This project is the backend for an e-commerce application, providing APIs for managing products, brands, and categories.

## Step 1: Project Configuration and Dependency Management ( start at commit-5)

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


## Step 4: Created Repository Layer 

In this step, we defined the repository interfaces for all entities, which will handle database operations.

### What we did:
1. Created repository interfaces for all entities, including:
   - `UserRepository`: To manage users by roles and email.
   - `AboutRepository`: To manage seller information.
   - `ProductRepository`: To fetch products by category, seller, or name.
   - `CategoryRepository`: To find categories by name.
   - `OrderRepository`: To manage orders by buyer, status, or date range.
   - `CartRepository`: To manage carts by buyer.
   - `PaymentRepository`: To fetch payments by order.
   - `ReviewRepository`: To manage reviews by product or buyer.
   - `ShipmentRepository`: To track shipments by courier or status.
   - `CouponRepository`: To find coupons by code or product.
   - `AddressRepository`: To manage user addresses.
   - `WishListRepository`: To manage wish lists by user.

### Used Structures, Annotations, and Libraries:
1. **`@Repository` Annotation**:
   - Marks the class as a data access component.
   - Enables Spring to recognize it as a Bean and integrate it into the exception handling mechanism.

2. **`JpaRepository` Interface**:
   - Extends Spring Data JPA’s repository framework to provide CRUD operations.
   - Used to automatically handle common database operations without writing SQL queries.

3. **Custom Query Methods**:
   - Query methods are derived from method names like `findByEmail` and `findByCategoryId` to perform specific searches.
   - Simplifies querying by avoiding manual SQL writing.

4. **Optional**:
   - Provides a safe way to handle potentially null values, preventing `NullPointerException`.

5. **List**:
   - Returns multiple records from the database and stores them in a list.

### Benefits:
- **JpaRepository**: Extending `JpaRepository` enables basic CRUD operations for each entity without the need to write any implementation code.
- **Custom Query Methods**: Additional methods for finding entities by specific attributes (e.g., `findByEmail`, `findByCategoryId`) enhance the flexibility of data access.


## Step 5: Created Exception Layer

In this step, we added custom exception classes and implemented a global error handling mechanism.

### What we did:
1. Created custom exceptions:
   - `ResourceNotFoundException`: Thrown when a resource cannot be found (returns 404 NOT FOUND).
   - `InvalidInputException`: Thrown when the input provided is invalid (returns 400 BAD REQUEST).
   - `OperationFailedException`: Thrown when an internal operation fails (returns 500 INTERNAL SERVER ERROR).
   - `AlreadyDeletedException`: Thrown when an attempt is made to delete an already deleted resource (returns 400 BAD REQUEST).
   - `SaveException`: Thrown when a save operation fails (returns 500 INTERNAL SERVER ERROR).

2. Implemented `GlobalExceptionHandler`:
   - Catches the custom exceptions and general exceptions across the application.
   - Formats the error responses with `ErrorDetails` which include a timestamp, error message, and details about the request.

### Used Structures, Annotations, and Libraries:

1. **`@ResponseStatus`**: Specifies the HTTP status code to be returned when the exception is thrown.
   - Example: `@ResponseStatus(HttpStatus.NOT_FOUND)` returns a 404 status code.

2. **`@ControllerAdvice`**: Global error handler that manages exceptions across the entire application.

3. **`@ExceptionHandler`**: Handles specific exceptions and returns a custom response for each.

4. **`ResponseEntity`**: Used to return custom HTTP responses, including status codes and messages.

5. **`WebRequest`**: Provides details about the incoming HTTP request, used in error reporting.

### Benefits:
- Centralized error handling ensures consistency in the application's response to errors.
- Custom exceptions make it easier to provide meaningful error messages to users.



## Step 6: Created DTO Layer

In this step, we created Data Transfer Objects (DTOs) to handle the data flowing between the client and the server without exposing the actual database entities.

### What we did:
1. Created DTOs for the following entities:
   - **User**:
      - `UserRequestDto`: Handles incoming data for creating or updating a user.
      - `UserResponseDto`: Returns user data without exposing sensitive information like passwords.
   - **Product**:
      - `ProductRequestDto`: Used for creating or updating product details, including product images.
      - `ProductResponseDto`: Returns product details, including category and seller names.
   - **Order**:
      - `OrderRequestDto`: Used for placing orders, including buyer ID and product IDs.
      - `OrderResponseDto`: Returns order details, including buyer name and product names.
   - **Review**:
      - `ReviewRequestDto`: Used for adding a review for a product or seller.
      - `ReviewResponseDto`: Returns review details, including the product and buyer names.
   - **WishList**:
      - `WishListRequestDto`: Handles adding products to a user's wish list.
      - `WishListResponseDto`: Returns wish list details, including user and product names.

### Used Structures, Annotations, and Libraries:

1. **Lombok Annotations**:
   - `@Data`: Automatically generates getter and setter methods along with `toString`, `equals`, `hashCode`, and constructors.
   - `@AllArgsConstructor`: Generates a constructor with all fields as parameters.
   - `@NoArgsConstructor`: Generates a no-argument constructor.
   - `@Builder`: Implements the builder pattern for more flexible object creation.

2. **Validation Annotations**:
   - `@NotNull`: Ensures that required fields in request DTOs are not null.
   - `@Email`: Validates email format in the `UserRequestDto`.

### Benefits:
- **Data Protection**: DTOs provide a layer of abstraction, protecting the internal structure of database entities.
- **Input Validation**: Request DTOs ensure that the data coming from the client meets the necessary requirements before reaching the service layer.
- **Lombok Integration**: Lombok reduces boilerplate code, making DTO classes cleaner and easier to maintain.
