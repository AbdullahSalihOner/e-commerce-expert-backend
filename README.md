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


## Step 12: Centralizing API Endpoints

In this step, we improved the maintainability of the project by centralizing all API endpoints into a dedicated `ApiEndpoints` class. This ensures that any changes to API routes can be easily managed in a single location without modifying each controller class.

### Changes Made:
- Created a `ApiEndpoints` class to store all API endpoint paths as constants.
- Updated all controller classes (`ProductController`, `OrderController`, `UserController`, `WishListController`, `ReviewController`) to use the constants from the `ApiEndpoints` class for defining the API paths.

### Benefits:
- **Maintainability**: Centralizing endpoint definitions reduces the risk of inconsistencies and makes it easier to update or change API routes in the future.
- **Code Readability**: By using constant names for routes, it becomes clearer which endpoints are being used and makes the code more readable.

### Example API Endpoints:
1. **OrderController**:
    - GET `/api/orders/{id}`
    - POST `/api/orders/place`
    - GET `/api/orders/all`

2. **ProductController**:
    - GET `/api/products/{id}`
    - POST `/api/products/add`
    - PUT `/api/products/update/{id}`
    - DELETE `/api/products/delete/{id}`
    - GET `/api/products/all`

3. **UserController**:
    - GET `/api/users/{id}`
    - POST `/api/users/create`
    - PUT `/api/users/update/{id}`
    - DELETE `/api/users/delete/{id}`

4. **WishListController**:
    - POST `/api/wishlists/add`
    - GET `/api/wishlists/user/{userId}`

5. **ReviewController**:
    - POST `/api/reviews/add`
    - GET `/api/reviews/product/{productId}`
    - DELETE `/api/reviews/delete/{id}`


## Step 13: Updated User Model with Multiple Roles

In this step, we modified the `User` model to support multiple roles by changing the `role` field from a single value to a `List<UserRole>`. This allows a user to have multiple roles such as `BUYER`, `SELLER`, `ADMIN`, etc.

### Changes Made:
- Updated the `User` model to support multiple roles (`List<UserRole>`).
- Modified `UserRequestDto` and `UserResponseDto` to handle roles as a list.
- Updated `UserMapper` to map lists of roles between the DTO and entity.
- Adjusted `UserService` to handle multiple roles during user creation and updates.
- Updated `UserController` to reflect the changes in how roles are managed.

### Benefits:
- **Flexibility**: A user can now have multiple roles, allowing the system to handle more complex use cases.
- **Role Management**: Roles can be easily managed and updated through the API with full flexibility.

### Example API Endpoints:
1. **UserController**:
    - GET `/api/users/{id}`: Get user by ID with roles.
    - POST `/api/users/create`: Create a new user with one or more roles.
    - PUT `/api/users/update/{id}`: Update user details and roles.
    - DELETE `/api/users/delete/{id}`: Delete a user.


## Step 14: Added Validation for User Email Uniqueness and Product Stock Check

In this step, we added mechanisms to ensure that:
1. When a user tries to register or update their profile, their email must be unique. If the email is already in use by another user, the system will return an error.
2. When placing an order, the system checks if the requested products have sufficient stock. If any of the products are out of stock, an error will be thrown and the order will not be processed.

### Changes Made:

#### **User Email Uniqueness Check:**
- Updated the `UserService` to check if the email is already in use when creating or updating a user.
- If the email is already in use, a `DuplicateResourceException` is thrown with an HTTP 409 (Conflict) status.
- Updated the `UserRepository` to include the `existsByEmail` method for checking email existence.

#### **Product Stock Check in OrderService:**
- Added logic to the `OrderService` to verify if the requested products have sufficient stock when placing an order.
- If any product is out of stock, an `OutOfStockException` is thrown with an HTTP 400 (Bad Request) status.

### Benefits:
- **User Management**: Ensures that no duplicate email addresses are used in the system, which enhances data integrity and user experience.
- **Order Management**: Prevents orders from being placed if the requested products are out of stock, which improves inventory management.

### Example API Endpoints:
1. **UserController**:
    - POST `/api/users/create`: Creates a new user, checks if the email is unique.
    - PUT `/api/users/update/{id}`: Updates user information, checks if the email is unique.

2. **OrderController**:
    - POST `/api/orders/place`: Places an order, checks product stock before processing the order.



# E-commerce Expert Backend

## Step 15: Redis Caching Integration with Docker

In this step, Redis caching has been integrated into the project using a standalone Redis instance running on Docker. This improves the performance of the application by reducing database calls and speeding up data access through caching.

### Technologies Used:
- **Spring Boot**: Backend framework used for building the REST API.
- **Redis**: In-memory data structure store, used for caching.
- **Docker**: Platform to run Redis as a container, enabling isolation and ease of setup.
- **JPA (Java Persistence API)**: Used for database interaction.
- **Lombok**: Simplifies Java class implementation by generating getters, setters, and constructors.
- **MapStruct**: For object mapping between DTOs and entities.

### Annotations Used:
- `@EnableCaching`: Enables Spring Boot's caching mechanism across the application.
- `@Cacheable`: Used on methods whose results should be stored in the cache. If the method is called again with the same parameters, the cached result is returned instead of executing the method again.
- `@CacheEvict`: Used to clear or invalidate cache entries when the associated data changes, ensuring stale data is not served.
- `@Service`: Declares a class as a Spring service.
- `@Configuration`: Declares a class as a source of bean definitions for the Spring IoC container.

### Steps Taken:
1. **Docker Setup**:
    - Redis is pulled from Docker Hub and run as a standalone container using the following commands:

      ```bash
      docker pull redis
      docker run -d --name redis-container -p 6379:6379 redis
      ```

2. **Redis Configuration**:
    - Added Redis configurations in `application.properties` to connect to the Dockerized Redis container:

      ```properties
      spring.redis.host=localhost
      spring.redis.port=6379
      spring.cache.type=redis
      ```

3. **Cache Configuration in Spring Boot**:
    - A `RedisConfig` class was added to configure the cache manager and define how the data is serialized and stored in Redis. Cache entries are configured to expire after 60 minutes.

4. **Service Layer Caching**:
    - Added caching to `OrderService` and `ProductService` using `@Cacheable` to cache method results and `@CacheEvict` to clear cache when relevant data is updated or deleted.

### Benefits:
- **Improved Performance**: Redis caching reduces the load on the database by storing frequently accessed data in memory.
- **Easy Management with Docker**: Redis is running as a Docker container, making it simple to start, stop, and manage in isolated environments.

### Example API Endpoints:
1. **OrderController**:
    - GET `/api/orders/{id}`: Fetches order details and caches the result for subsequent requests.
    - DELETE `/api/orders/{id}`: Deletes an order and clears the related cache entry.

2. **ProductController**:
    - GET `/api/products/{id}`: Fetches product details and caches the result.
    - DELETE `/api/products/{id}`: Deletes a product and clears the cache.

### Running the Redis container:
To run Redis on Docker, use the following commands:

```bash
docker pull redis
docker run -d --name redis-container -p 6379:6379 redis
```

## Step 16: Added Swagger/OpenAPI Annotations for API Documentation

In this step, we enhanced the documentation of our API using Swagger/OpenAPI annotations. We applied the following changes across all controller classes:

### Changes Made:

1. **Tagging Controllers with @Tag**:
   - Each controller now has a `@Tag` annotation that describes the purpose of the controller (e.g., User Controller, Order Controller, etc.).
   - This helps in organizing the API documentation and providing meaningful descriptions for API consumers.

2. **Documenting API Methods with @Operation**:
   - Each API method (GET, POST, PUT, DELETE) is annotated with `@Operation`, providing a summary and description of what the method does.
   - This gives API users clear insights into each method's purpose and behavior, improving API discoverability.

### Benefits:
- **Improved API Documentation**: With the `@Tag` and `@Operation` annotations, the generated API documentation becomes more user-friendly and informative.
- **Better Organization**: Grouping API endpoints by their logical controller helps in understanding the API structure better.
- **Enhanced Readability**: Descriptive summaries and explanations for each API method make it easier for developers to interact with the API.

### Example API Endpoints:
1. **UserController**:
   - GET `/api/users/{id}`: Retrieves a user by ID.
   - POST `/api/users/create`: Creates a new user.
   - DELETE `/api/users/delete/{id}`: Deletes a user by ID.

2. **OrderController**:
   - GET `/api/orders/{id}`: Retrieves an order by ID.
   - POST `/api/orders/place`: Places a new order.
   - DELETE `/api/orders/cancel/{id}`: Cancels an order by ID.
   
3. **ProductController**:
   - GET `/api/products/{id}`: Retrieves a product by ID.
   - POST `/api/products/add`: Adds a new product.
   - DELETE `/api/products/delete/{id}`: Deletes a product by ID.

4. **ReviewController**:
   - GET `/api/reviews/{id}`: Retrieves a review by ID.
   - POST `/api/reviews/add`: Adds a new review.
   - DELETE `/api/reviews/delete/{id}`: Deletes a review by ID.

5. **WishListController**:
   - GET `/api/wishlists/{id}`: Retrieves a wishlist by user ID.
   - POST `/api/wishlists/add`: Adds a new wishlist.
   - DELETE `/api/wishlists/delete/{id}`: Deletes a wishlist by ID.



## 17. Step - Breadcrumb and Rate Limiting Integration in Controllers

### What We Did:
In this step, we have integrated breadcrumb functionality and rate limiting into all our controller classes (`UserController`, `WishListController`, `ReviewController`, `ProductController`, and `OrderController`). Breadcrumbs provide a better navigation experience by showing the user's path in the application, while rate limiting ensures that the system is protected from too many requests in a short period.

#### Key Changes:
1. **Breadcrumb Integration**:
   - Each controller now generates breadcrumb trails based on the current API path.
   - Breadcrumbs are returned in the API response, making it easier to track user flow and navigation.

2. **Rate Limiting**:
   - We implemented rate limiting to all controller methods using the `Bucket4j` library.
   - The limit is set to 100 requests per minute to prevent abuse or excessive requests.

3. **OpenAPI Documentation**:
   - Added `@Tag` annotation for each controller to group related APIs together in Swagger.
   - Added `@Operation` annotation for each method to provide a detailed description in Swagger UI.

### Controllers and API Endpoints:

#### 1. **UserController**:
- **Base URL:** `/api/users`
- **Endpoints:**
   - `GET /all` – Fetch all users with breadcrumb trail.
   - `GET /{id}` – Fetch user by ID with breadcrumb trail.
   - `POST /create` – Create a new user.
   - `PUT /update/{id}` – Update a user.
   - `DELETE /delete/{id}` – Delete a user.

#### 2. **WishListController**:
- **Base URL:** `/api/wishlists`
- **Endpoints:**
   - `GET /all` – Fetch all wish lists with breadcrumb trail.
   - `GET /user/{id}` – Fetch wish list by user ID with breadcrumb trail.
   - `POST /add` – Add a new wish list.
   - `DELETE /delete/{id}` – Delete a wish list.

#### 3. **ReviewController**:
- **Base URL:** `/api/reviews`
- **Endpoints:**
   - `GET /all` – Fetch all reviews with breadcrumb trail.
   - `GET /product/{id}` – Fetch review by product ID with breadcrumb trail.
   - `POST /add` – Add a new review.
   - `PUT /update/{id}` – Update a review.
   - `DELETE /delete/{id}` – Delete a review.

#### 4. **ProductController**:
- **Base URL:** `/api/products`
- **Endpoints:**
   - `GET /all` – Fetch all products with breadcrumb trail.
   - `GET /{id}` – Fetch product by ID with breadcrumb trail.
   - `GET /category/{categoryId}` – Fetch products by category with breadcrumb trail.
   - `POST /add` – Add a new product.
   - `PUT /update/{id}` – Update a product.
   - `DELETE /delete/{id}` – Delete a product.

#### 5. **OrderController**:
- **Base URL:** `/api/orders`
- **Endpoints:**
   - `GET /all` – Fetch all orders with breadcrumb trail.
   - `GET /{id}` – Fetch order by ID with breadcrumb trail.
   - `POST /place` – Place a new order.
   - `DELETE /cancel/{id}` – Cancel an order.

### Technologies Used:
- **Bucket4j**: Implemented for rate limiting (100 requests per minute).
- **Breadcrumb Service**: Custom breadcrumb generation to track user navigation path.
- **Swagger Annotations** (`@Tag`, `@Operation`): Used to document and structure our API operations in Swagger UI for better API documentation.

### Example API Response with Breadcrumb:
```json
{
  "users": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  ],
  "breadcrumbs": [
    {
      "label": "Home",
      "url": "/"
    },
    {
      "label": "Users",
      "url": "/users"
    }
  ]
}
```


## Step 18: Category Management and Breadcrumb Integration

### What We Did:
In this step, we added full support for the `Category` entity, including:
- **DTOs** for transferring data to and from the API.
- **Service Layer** to manage all Category-related operations (CRUD).
- **Controller Layer** to expose Category management endpoints.
- **Breadcrumb Integration**: We integrated **BreadcrumbService** into each endpoint in the CategoryController to allow tracking and managing user navigation paths.
- **Rate Limiting**: Ensured that each API endpoint has rate limiting applied using Bucket4j.
- **Swagger Documentation**: All CategoryController methods are documented using Swagger with operation summaries.

### Changes Made:
1. **Category DTOs**:
   - Created `CategoryRequestDto` for creating and updating category data.
   - Created `CategoryResponseDto` for sending category information as a response.

2. **Service Layer**:
   - `ICategoryService`: Interface defining the required methods for category operations.
   - `CategoryService`: Implementation of `ICategoryService` providing logic for category creation, update, deletion, and retrieval.
   - Added loggers for better visibility into service operations.

3. **Mapper Layer**:
   - `CategoryMapper` was implemented using **MapStruct** to map between Category entities and DTOs seamlessly.

4. **Controller Layer**:
   - **CategoryController**: Provides RESTful endpoints for managing product categories.
   - Each method in `CategoryController` now includes:
     - **Rate Limiting**: Limiting requests to 100 per minute using Bucket4j.
     - **BreadcrumbService**: Tracks the navigation path of each API call and stores it.
     - **Swagger/OpenAPI** documentation with operation summaries for easy integration with Swagger UI.
   
5. **Breadcrumb Integration**:
   - Breadcrumb information is automatically tracked and stored whenever a user interacts with Category APIs, providing a full navigation trail.
   - This helps in tracking how users are navigating through the application.

6. **Rate Limiting**:
   - Implemented rate limiting across all endpoints in `CategoryController` to avoid overuse of resources.

### API Endpoints:

| HTTP Method | Endpoint                              | Description                        |
|-------------|---------------------------------------|------------------------------------|
| GET         | `/api/categories/all`                 | Get all categories                 |
| GET         | `/api/categories/{id}`                | Get a category by its ID           |
| POST        | `/api/categories/create`              | Create a new category              |
| PUT         | `/api/categories/update/{id}`         | Update an existing category by ID  |
| DELETE      | `/api/categories/delete/{id}`         | Delete a category by its ID        |

### Technologies & Libraries Used:
- **Spring Boot**: Framework for developing the API.
- **MapStruct**: For mapping between DTOs and entities.
- **Bucket4j**: For rate limiting the API requests.
- **BreadcrumbService**: To track and store the user's navigation path.
- **Swagger/OpenAPI**: For auto-generating API documentation.


## Step 19: Shipment Management and Order Status Update

### What We Did:
In this step, we added support for managing shipments (shipping details) and extended the order functionality by allowing order status updates.

### Changes Made:

1. **Shipment DTOs**:
    - **ShipmentRequestDto**: Contains the necessary fields to create and update shipment information (e.g., order ID, courier, tracking number, estimated delivery date).
    - **ShipmentResponseDto**: DTO used for returning shipment data (ID, status, tracking info).

2. **Shipment Service Layer**:
    - Created `IShipmentService` interface to define shipment operations such as adding, updating, and deleting shipments.
    - Implemented `ShipmentService` to manage CRUD operations and status updates for shipments.
    - Added logging to provide better visibility into shipment management operations.

3. **Shipment Mapper**:
    - Used **MapStruct** to map between `Shipment` entity and DTOs.
    - `ShipmentMapper` handles the transformation between DTOs and the shipment entity to simplify controller and service logic.

4. **Shipment Controller**:
    - **ShipmentController** provides RESTful endpoints for managing shipments:
        - Get all shipments.
        - Get a shipment by ID.
        - Create, update, and delete a shipment.
        - Update the status of a shipment (e.g., Pending, Shipped, Delivered).
    - Integrated **BreadcrumbService** into each method to generate breadcrumb navigation paths based on user actions.
    - Each API method is protected with **Rate Limiting** (100 requests per minute) to prevent abuse.

5. **Order Status Update**:
    - Added an endpoint to update the status of an order in `OrderController`.
    - **OrderService** now has a method for changing the status of an order (e.g., from Pending to Delivered).

6. **Breadcrumb Integration**:
    - **BreadcrumbService** was integrated into `ShipmentController`, allowing dynamic breadcrumb generation based on the user's navigation path.
    - Breadcrumbs are added to the HTTP response header to assist in frontend breadcrumb displays.

7. **Rate Limiting**:
    - Implemented rate limiting in `ShipmentController` to limit API calls to 100 requests per minute using **Bucket4j**.

### API Endpoints for Shipments:

| HTTP Method | Endpoint                                    | Description                           |
|-------------|---------------------------------------------|---------------------------------------|
| GET         | `/api/shipments/all`                        | Get all shipments                     |
| GET         | `/api/shipments/{id}`                       | Get a shipment by ID                  |
| POST        | `/api/shipments/create`                     | Create a new shipment                 |
| PUT         | `/api/shipments/update/{id}`                | Update an existing shipment by ID     |
| PUT         | `/api/shipments/update-status/{id}`         | Update the status of a shipment       |
| DELETE      | `/api/shipments/delete/{id}`                | Delete a shipment by ID               |

### API Endpoints for Order Status Update:

| HTTP Method | Endpoint                                    | Description                           |
|-------------|---------------------------------------------|---------------------------------------|
| PUT         | `/api/orders/update-status/{id}`            | Update the status of an order         |

### Technologies & Libraries Used:
- **Spring Boot**: Framework for developing the API.
- **MapStruct**: For mapping between DTOs and entities.
- **Bucket4j**: For rate limiting the API requests.
- **BreadcrumbService**: To track and store the user's navigation path dynamically.
- **Swagger/OpenAPI**: For auto-generating API documentation.


# Step 20: JSON Serialization for Product Images

## What We Did:
In this step, we added support for handling product images as a list of URLs (`List<String>`) by storing them in the database as a JSON string. This ensures that multiple images can be associated with a product efficiently.

## Changes Made:

### Product Entity Update:
- The `images` field in the `Product` entity was updated to store `List<String>` as a JSON string in the database.
- Getter and setter methods were added to convert the `List<String>` to a JSON string when saving, and back to a list when retrieving.

### ProductMapper:
- Custom methods were added in the `ProductMapper` to handle the conversion between the `List<String>` of images and the JSON string stored in the database.
- Used Jackson's `ObjectMapper` within the mapper to facilitate this conversion.

### Product Service Layer:
- Updated the `ProductService` to correctly map images between the `ProductRequestDto` and `Product` entity when saving and retrieving products.
- Ensured that images are correctly processed when creating or updating a product.

### Product DTOs:
- **ProductRequestDto**: The `images` field remains as a `List<String>`, allowing for a clean API contract without exposing internal storage details.
- **ProductResponseDto**: Returns the list of image URLs (`List<String>`) to the API consumer, keeping it consistent with the request format.

### Product Controller:
- No major changes needed in the controller, as the logic for handling the images was abstracted into the service and mapper layers.
- The `ProductController` still provides RESTful endpoints for creating, updating, and retrieving products, with the `images` field being handled transparently.

## API Endpoints for Product Images:
| HTTP Method | Endpoint                      | Description                                             |
|-------------|-------------------------------|---------------------------------------------------------|
| POST        | `/api/products/add`            | Add a new product with images (as `List<String>`)        |
| PUT         | `/api/products/update/{id}`    | Update an existing product with new images               |
| GET         | `/api/products/{id}`           | Retrieve product details including image URLs            |

## Technologies & Libraries Used:
- **Spring Boot**: Framework for building the API.
- **Jackson ObjectMapper**: For converting `List<String>` to a JSON string and vice versa.
- **MapStruct**: For mapping between DTOs and entities, with custom methods for image handling.
- **Bucket4j**: For rate limiting the API requests.
- **Swagger/OpenAPI**: For auto-generating API documentation.

## Benefits:
- **Efficient Storage**: By storing the image URLs as a JSON string, we reduce the complexity of managing related entities for product images.
- **Scalable**: This approach allows for easy scalability, as the number of images per product can grow without requiring schema changes.
- **API Simplicity**: The API remains simple and clean, allowing clients to work with a `List<String>` for images, while the storage complexity is handled in the service layer.

## Example API Request for Product Creation:

```json
{
  "name": "Sample Product",
  "description": "This is a sample product with multiple images.",
  "price": 150.00,
  "stockQuantity": 30,
  "categoryId": 1,
  "userId": 2,
  "images": [
    "https://example.com/image1.jpg",
    "https://example.com/image2.jpg",
    "https://example.com/image3.jpg"
  ]
}
```

## Step 21: Bug Fix - `content` Field and `JSON` Storage Handling

### Changes:
1. **ProductService Update**:
    - Added null checks for product listing, fetching by ID, and category-based searches.
    - When products are null or empty, a `404 Not Found` error is returned.

2. **ProductController Update**:
    - Enhanced breadcrumb generation. If breadcrumbs or product lists are null, an empty list is returned instead.
    - All results are checked, and correct HTTP responses are ensured using `ResponseEntity`.

3. **ProductMapper Update**:
    - Added error handling for converting the `images` field into JSON format.
    - A `RuntimeException` is thrown if there are errors in JSON string conversion.

### API Endpoints with Improved Error Handling:
- **Get All Products**: `/api/products/all`
- **Get Product by ID**: `/api/products/{id}`
- **Get Products by Category**: `/api/products/category/{categoryId}`


## Step 22: CORS, RestTemplate Configuration, User Authentication and Signup

### 1. CORS (Cross-Origin Resource Sharing) Configuration

In this step, a `WebConfig` class was added to the project to allow cross-origin requests from any origin. This is essential when building APIs that need to be accessible by clients hosted on different domains.

**Used Annotation:**
- `@Configuration`: Indicates that the class can be used by the Spring IoC container as a source of bean definitions.
- `WebMvcConfigurer`: Allows customizing Spring MVC's default configurations.

**Benefits:**
- Allows controlled access from external domains or APIs.
- Necessary for frontend and mobile clients to interact with the backend.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowedHeaders("*");
    }
}
```
