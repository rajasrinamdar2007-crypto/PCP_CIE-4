# Inventory Management System

A RESTful Inventory Management System built using **Java, Spring Boot, Spring Data JPA, and MySQL**. The application allows users to manage products, track stock movements, and identify products that are running low on stock.

## Features

- **Product Management:** Add new products and retrieve product details using their ID.
- **Stock Management:** Record inward and outward stock movements.
- **Stock Tracking:** Automatically updates product stock whenever a movement is recorded.
- **Low Stock Alerts:** Identifies products whose current stock is less than or equal to their minimum stock threshold.
- **Movement History:** View all inventory movements or filter movements by product.
- **Data Persistence:** Stores product and inventory movement records in a MySQL database.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 19 | Programming language |
| Spring Boot 4.1.2 | Backend application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM and database table management |
| MySQL | Relational database |
| Maven | Dependency management and build tool |
| Lombok | Reduces boilerplate Java code |
| Postman | API testing |

## Project Structure

```text
PCP_CIE-4/
│
├── src/
│   ├── main/
│   │   ├── java/com/warehouse/inventory/
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── ProductController.java
│   │   │   │   └── InventoryMovementController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   └── MovementRequest.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── Product.java
│   │   │   │   └── InventoryMovement.java
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── InventoryMovementRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── ProductService.java
│   │   │   │   └── InventoryMovementService.java
│   │   │   │
│   │   │   └── InventoryApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── .mvn/
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Application Architecture

The application follows a layered architecture:

1. **Controller Layer:** Handles incoming HTTP requests and returns responses.
2. **Service Layer:** Contains the business logic for product and inventory operations.
3. **Repository Layer:** Communicates with the MySQL database using Spring Data JPA.
4. **Entity Layer:** Defines the database entities and their relationships.
5. **DTO Layer:** Transfers inventory movement request data.
6. **Exception Layer:** Handles application exceptions and returns appropriate HTTP error responses.

## Database Design

The application uses a MySQL database named `warehouse`.

### 1. Products Table

Stores product information.

| Column | Description |
|---|---|
| id | Unique product ID |
| sku | Unique product identification code |
| name | Product name |
| current_stock | Current available stock |
| minimum_stock_threshold | Minimum stock limit |
| price | Product price |

### 2. Inventory Movements Table

Stores inward and outward stock transactions.

| Column | Description |
|---|---|
| id | Unique movement ID |
| product_id | Reference to the associated product |
| movement_type | INWARD or OUTWARD |
| quantity | Quantity moved |
| movement_date | Date and time of movement |
| remarks | Additional information |

**Relationship:** Each inventory movement is associated with one product through a many-to-one relationship.

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK)
- MySQL Server
- Maven (or use the included Maven Wrapper)
- Postman (for API testing)

### 1. Clone the Repository

```bash
git clone https://github.com/rajasrinamdar2007-crypto/PCP_CIE-4.git
```

Navigate to the project directory:

```bash
cd PCP_CIE-4
```

### 2. Configure MySQL

Start your MySQL server and make sure your database credentials are configured correctly.

The application uses the following database configuration in `src/main/resources/application.properties`:

```properties
spring.application.name=inventory

spring.datasource.url=jdbc:mysql://localhost:3306/warehouse?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.

The application automatically creates the `warehouse` database if it does not exist. Hibernate creates or updates the required tables.

**Security Note:** Do not commit database passwords or other credentials to a public repository. Use environment variables or a local configuration file excluded from Git.

### 3. Run the Application

On Windows, execute:

```bash
mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

The application starts at:

```text
http://localhost:8080
```

## REST API Endpoints

The following endpoints are implemented in the application.

### Product APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Add a new product |
| GET | `/api/products/{id}` | Retrieve a product by ID |
| GET | `/api/products/alerts/low-stock` | Retrieve products with low stock |

**Example: Add a Product**

```http
POST http://localhost:8080/api/products
```

Request Body:

```json
{
  "sku": "SKU101",
  "name": "Keyboard",
  "currentStock": 50,
  "minimumStockThreshold": 10,
  "price": 799.0
}
```

### Inventory Movement APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/movements` | Record an inventory movement |
| GET | `/api/movements` | Retrieve all inventory movements |
| GET | `/api/movements/product/{productId}` | Retrieve movements for a specific product |

**Example: Record an Inward Movement**

```http
POST http://localhost:8080/api/movements
```

Request Body:

```json
{
  "productId": 1,
  "movementType": "INWARD",
  "quantity": 20,
  "remarks": "New stock received"
}
```

**Example: Record an Outward Movement**

```json
{
  "productId": 1,
  "movementType": "OUTWARD",
  "quantity": 5,
  "remarks": "Stock issued"
}
```

The application automatically updates the product's current stock after recording a movement.

- `INWARD` increases the available stock.
- `OUTWARD` decreases the available stock.
- An outward movement is rejected if sufficient stock is not available.
- Invalid movement types are rejected.

### Low Stock Alerts

Use the following endpoint to retrieve products whose current stock is less than or equal to their minimum stock threshold.

```http
GET http://localhost:8080/api/products/alerts/low-stock
```

The application also prints a low-stock alert in the console when a recorded movement causes a product to reach or fall below its threshold.

## Exception Handling

The application includes a global exception handler using `@RestControllerAdvice`.

It handles errors such as:

- Product not found.
- Insufficient stock.
- Invalid inventory movement type.

Error responses contain a timestamp and an error message.

## Testing

The REST APIs can be tested using **Postman**.

1. Start the MySQL server.
2. Run the Spring Boot application.
3. Open Postman.
4. Send HTTP requests to the endpoints listed above.
5. Check the responses and verify the updated records in MySQL Workbench.

## Future Improvements

- Add product update and deletion operations.
- Add product listing and search functionality.
- Implement authentication and role-based access.
- Develop a frontend dashboard for inventory monitoring.
- Add advanced inventory reports and analytics.

---

**Developed using Java, Spring Boot, Spring Data JPA, and MySQL.**
