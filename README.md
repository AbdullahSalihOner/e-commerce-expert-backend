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


## Step 7: Created Mapper Layer with MapStruct

In this step, we used MapStruct to create mappers for converting between entities and DTOs, simplifying the data transformation process.

### What we did:
1. Created mapper interfaces for the following entities:
   - **User**: Converts between `User` entity and `UserRequestDto` and `UserResponseDto`.
   - **Product**: Converts between `Product` entity and `ProductRequestDto` and `ProductResponseDto`, manually mapping the category and seller names.
   - **Order**: Converts between `Order` entity and `OrderRequestDto` and `OrderResponseDto`, including custom mapping for product names in orders.
   - **Review**: Converts between `Review` entity and `ReviewRequestDto` and `ReviewResponseDto`, mapping product and buyer names.
   - **WishList**: Converts between `WishList` entity and `WishListRequestDto` and `WishListResponseDto`, with custom mapping for product names.

### Used Structures, Annotations, and Libraries:

1. **MapStruct**:
   - `@Mapper(componentModel = "spring")`: Defines the class as a mapper and integrates it with Spring.
   - `@Mapping`: Custom field mappings between entities and DTOs, such as mapping `category.name` to `categoryName` in product DTOs.
   - `@Named`: Used for custom mapping methods like transforming a list of products into a list of product names.

2. **List**:
   - Used to manage and transform lists of entities or DTOs, such as converting a list of products in an order to a list of product names.

3. **Stream API**:
   - Java 8 streams are used to efficiently transform lists, such as extracting product names from a list of products in an order or wish list.

### Benefits:
- **Automated Mapping**: MapStruct simplifies and automates the conversion process between entities and DTOs, reducing boilerplate code.
- **Custom Mappings**: Allows for custom field mappings where necessary, providing flexibility in how data is transferred between layers.
- **Integration with Spring**: MapStruct integrates seamlessly with Spring, making it easy to inject and use mappers throughout the application.


## Step 8: Created Service Layer with Logging and Result Handling

In this step, we implemented the service layer for business logic, interacting with repositories and mappers. Additionally, we introduced logging to track application behavior and created **Result** and **DataResult** classes for standardized response handling.

### What we did:
1. Implemented services for the following entities:
   - **UserService**:
      - Provides methods for listing, adding, updating, and deleting users.
   - **ProductService**:
      - Provides methods for listing, adding, updating, and deleting products.
   - **OrderService**:
      - Allows placing orders and retrieving order details.
   - **ReviewService**:
      - Handles adding and retrieving product reviews.
   - **WishListService**:
      - Manages the user's wish lists, allowing users to save products for later.

2. Integrated logging:
   - **SLF4J Logger**: Used to log important events like successful operations, warnings, and errors.
   - **Log Levels**:
      - `info`: Logs successful operations (e.g., "Product added successfully").
      - `warn`: Logs potential issues (e.g., "No products found").
      - `error`: Logs errors and exceptions (e.g., "Product not found with ID").

3. Introduced **Result** and **DataResult** for standardized response handling:
   - **Result**:
      - Provides a way to return a success or failure message with an associated status code.
      - Used in service methods to indicate if an operation was successful or failed.
   - **DataResult**:
      - Extends `Result` to include the actual data being returned from the service layer (e.g., product details).
      - Provides both the operation status and the resulting data (if available).

### Used Structures, Annotations, and Libraries:

1. **`@Service` Annotation**:
   - Marks the class as a service component to handle business logic.
   - Makes the class available for dependency injection in the application context.

2. **`@RequiredArgsConstructor`**:
   - Provided by Lombok to automatically generate a constructor for final fields, simplifying dependency injection.

3. **`Logger`**:
   - Used for tracking and logging application behavior.
   - Key operations are logged to provide insights into the application’s flow.

4. **Result and DataResult**:
   - **Result**: Used to represent the outcome of an operation, whether it was successful or not.
   - **DataResult**: Extends `Result` and includes data (such as a list of products or a specific user) as part of the response.

### Benefits:
- **Centralized Business Logic**: The service layer keeps business logic separate from controllers and repositories, improving code organization and maintainability.
- **Logging**: Logs provide real-time tracking and diagnostics of application behavior, making it easier to monitor and debug issues.
- **Standardized Responses**: Using `Result` and `DataResult` ensures a consistent way of handling and returning operation outcomes across the application.



## Step 9: Created Controller Layer with API Endpoints

In this step, we created REST API endpoints for all core services in the application. The endpoints are grouped based on the resources they manage (e.g., users, products, orders, etc.).

### What we did:
1. Created REST controllers for the following entities:
   - **UserController**: Handles user-related operations (listing, adding, updating, and deleting users).
      - GET: `/api/users/all`
      - GET: `/api/users/{id}`
      - POST: `/api/users/add`
      - PUT: `/api/users/update/{id}`
      - DELETE: `/api/users/delete/{id}`
   - **ProductController**: Manages product-related operations (listing, adding, updating, and deleting products).
      - GET: `/api/products/all`
      - GET: `/api/products/{id}`
      - GET: `/api/products/category/{categoryId}`
      - POST: `/api/products/add`
      - PUT: `/api/products/update/{id}`
      - DELETE: `/api/products/delete/{id}`
   - **OrderController**: Handles operations related to placing and viewing orders.
      - GET: `/api/orders/all`
      - GET: `/api/orders/{id}`
      - POST: `/api/orders/place`
   - **ReviewController**: Manages adding and retrieving reviews for products or sellers.
      - GET: `/api/reviews/all`
      - GET: `/api/reviews/{id}`
      - POST: `/api/reviews/add`
   - **WishListController**: Allows users to manage their wish lists.
      - GET: `/api/wishlists/all`
      - GET: `/api/wishlists/{id}`
      - POST: `/api/wishlists/add`

### Used Structures, Annotations, and Libraries:

1. **`@RestController`**:
   - Marks the class as a Spring REST controller that handles HTTP requests and returns HTTP responses.

2. **`@RequestMapping`**:
   - Defines the base URL mapping for each controller (e.g., `/api/users` for `UserController`).

3. **`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`**:
   - Defines specific HTTP methods for each endpoint (GET, POST, PUT, DELETE).

4. **`ResponseEntity`**:
   - Used to wrap responses with HTTP status codes and body content.

5. **`@Valid`**:
   - Validates request bodies, ensuring that required fields are present and correctly formatted.

### Benefits:
- **RESTful API**: Controllers expose a RESTful API to allow clients to interact with the application via standard HTTP methods.
- **Modular Structure**: Each controller is responsible for handling requests related to a specific entity, improving the organization and scalability of the application.
- **Annotation-Based Configuration**: Spring annotations simplify the process of defining API endpoints and mapping them to controller methods.


## Step 10: Added Rate Limiting to Controller Layer

In this step, we added rate limiting to the controller layer using Bucket4j. Each API has a limit of 100 requests per minute. If the limit is exceeded, the API responds with HTTP 429 (Too Many Requests).

### API Endpoints with Rate Limiting:

1. **UserController**:
    - GET: `/api/users/all`
    - GET: `/api/users/{id}`
    - POST: `/api/users/add`
    - PUT: `/api/users/update/{id}`
    - DELETE: `/api/users/delete/{id}`

2. **ProductController**:
    - GET: `/api/products/all`
    - GET: `/api/products/{id}`
    - GET: `/api/products/category/{categoryId}`
    - POST: `/api/products/add`
    - PUT: `/api/products/update/{id}`
    - DELETE: `/api/products/delete/{id}`

3. **OrderController**:
    - GET: `/api/orders/all`
    - GET: `/api/orders/{id}`
    - POST: `/api/orders/place`

4. **ReviewController**:
    - GET: `/api/reviews/all`
    - GET: `/api/reviews/{id}`
    - POST: `/api/reviews/add`

5. **WishListController**:
    - GET: `/api/wishlists/all`
    - GET: `/api/wishlists/{id}`
    - POST: `/api/wishlists/add`

### Rate Limiting:
- Each API endpoint is limited to 100 requests per minute using Bucket4j.
- If the rate limit is exceeded, the API responds with **HTTP 429 (Too Many Requests)**.


## Step 11: Updated Model Relationships with User Entity

In this step, we have ensured that the `User` entity is consistently referenced across all related models. All layers, including Models, DTOs, Mappers, Services, and Controllers, have been updated to use `User` with the same name for related entities. The changes allow for easier management of user-related data across the system.

### Changes Made:
- **Models**: Updated all models that have a relationship with the `User` entity. The `User` reference now consistently uses the `user` field for relationships.
- **DTOs**: All DTOs now handle `userId` and `userName` fields for entities related to the `User` entity.
- **Mappers**: Updated MapStruct mappers to map `userId` and `userName` between DTOs and entities.
- **Services**: Updated service methods to handle the updated `User` references in models and DTOs.
- **Controllers**: Updated controllers to manage user-related operations with the new structure.

### Example API Endpoints:
1. **OrderController**:
    - GET `/api/orders/{id}`: Fetches order details by order ID, including user information.
    - POST `/api/orders/place`: Places a new order, associating it with a user.

2. **ProductController**:
    - GET `/api/products/{id}`: Fetches product details by product ID, including user (seller) information.
    - POST `/api/products/add`: Adds a new product, associating it with a user (seller).

### Benefits:
- **Consistency**: All layers in the system now consistently use `User` as the entity name, making it easier to understand and maintain relationships with the `User` entity.
- **Clear Mapping**: DTOs and Mappers clearly map `userId` and `userName`, making data handling more straightforward.
