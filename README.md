# Price discount calculator
A RESTful application for managing discount policies, calculating product prices by applying discounts for given product and quantity in a scalable manner. This project uses Spring Boot with Java 21, Maven, Docker and Postgres database and supports dynamic discount policies based on amount and percentage thresholds.

## Features
- **Discount Policies Management**: Creates discount policies for given product UUID in database.
- **Discount Policies**:
    - **Amount-Based Discounts**: Amount discount based on quantity. The more pieces of the product are ordered, the bigger the
      discount is (e.g., 10 items – 2 USD off, 100 items – 5 USD off etc).
    - **Percentage-Based Discounts**: Percentage discount based on quantity. The more items ordered;
      the bigger the percentage discount is. (e.g., 10 items, 3% off, 50 items, 5% off etc).
- **Price Calculation**: Calculates final price after applying applicable discounts.
- **Validation**: Includes detailed input validation with domain-specific rules.
- **Database Integration**: Uses PostgreSQL database.
- **Docker Support**: Containerized application for easy deployment.

## Requirements
- Java 21
- Docker

## Setup Instructions
### **1. Clone the Repository**

    git clone https://github.com/azWorkshop/price-discount-calculator.git
    cd PriceDiscountCalculator

### **2. Build the application**

    mvn clean package -Pskip-dd

### **2. Build and run docker containers for application and database with Docker Compose**

    docker-compose up --build

The application will be available at http://localhost:8080.

## API Endpoints
API Enpoints can be viewed using Swagger UI with `http://localhost:8080/swagger-ui/index.html` or as OpenAPI document available at `http://localhost:8080/api-docs` when docker container with application is working.

### **1. Calculate Price**
#### **GET** /products/{uuid}/price?quantity=10

### **2. Add Amount-Based Discount**
#### **POST** /discounts/amount
Body:
```
{
  "threshold": 3,
  "discount": 5,
  "product": "d35e8da1-601e-4be4-8e24-66968f7b95e9"
}
```
### **3. Add Percentage-Based Discount**
#### **POST** /discounts/percentage
Body:
```
{
    "threshold": 3,
    "percentage": 1.5,
    "product": "d35e8da1-601e-4be4-8e24-66968f7b95e9"
}
```

## Database Schema

- **id** SERIAL PRIMARY KEY: This represents the auto-incremented ID for each policy (mapped to Long id).
- **product_id** UUID NOT NULL: Represents the UUID productId, which is used to associate the policy with a specific product.
- **policy_type** VARCHAR(255) NOT NULL: This is the discriminator column (@DiscriminatorColumn(name = "policy_type")) which will hold the type of policy (e.g., "Amount", "Percentage").
- **threshold** INT NOT NULL: Maps to the int threshold field in the entity, representing the threshold for discount eligibility.
- **amount** NUMERIC(10, 2): Maps to the BigDecimal amount field. This is defined with a precision of 10 digits, with 2 digits after the decimal point, suitable for currency.
- **percentage** DOUBLE PRECISION: Maps to the double percentage field. This is suitable for storing percentage values with decimals

## Future Enhancements
* unit & integration tests
* security
* monitoring

## Assumptions
Due to the lack of information regarding the product source, a `ProductSource` interface has been created. This interface can have multiple implementations providing products, such as through RestAPI, a database, a JSON file, or in-memory storage. 
By default, the application uses an in-memory implementation, , which provides two products with the following UUIDs:
- `d35e8da1-601e-4be4-8e24-66968f7b95e9` (price: 10.99)
- `ffe172d4-34c2-4b6c-b938-a25429d599a3` (price: 4.59)

To test adding a discount policy and calculating the final price, you need to add a policy that includes the UUID of one of these products.