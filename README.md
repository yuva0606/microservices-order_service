# Order Service - Ecommerce BackEnd

## Overview
The **Order Service** is a critical part of the **Ecommerce BackEnd**, responsible for managing customer orders. It facilitates order creation and retrieval while ensuring seamless integration with the **Product** and **Inventory Services** to maintain accurate stock levels.

## Purpose
The **Order Service** enables:
- **Order Creation**: Handles placing new orders, ensuring product availability.
- **Order Retrieval**: Allows retrieval of past orders for customers and administrators.
- **Inventory Synchronization**: Updates inventory levels through synchronous and asynchronous communication.

## Key Features
- **Real-Time Inventory Updates**: During order creation, communicates synchronously with the **Product Service** to verify stock availability and asynchronously with the **Inventory Service** via **Azure Service Bus** to reduce stock.
- **Order Management**: Handles core operations such as placing and retrieving orders.
- **Secure Operations**: Provides secure endpoints to prevent unauthorized access.

## API Endpoints
Here are the primary endpoints of the **Order Service**:

| Endpoint              | Method | Description                       | Access       |
|-----------------------|--------|-----------------------------------|--------------|
| `/orders`            | POST   | Place a new order                 | Authenticated Users |
| `/orders/{id}`       | GET    | Retrieve order details by ID      | Authenticated Users |
| `/orders/user/{userId}` | GET    | Retrieve orders for a specific user | Authenticated Users |
| `/orders/all`        | GET    | Retrieve all orders (admin only)  | Admin Only   |

> **Note**: Specific details of request payloads and responses depend on the API design and are documented in the service's Swagger documentation.

## Dependencies
- **Java 17** (or compatible version)
- **Spring Boot**: Provides the foundational framework for the service.
- **MySQL**: Stores order data, enabling quick retrieval and updates.
- **Azure Service Bus**: Facilitates asynchronous communication with the **Inventory Service**.

## Configuration
Key configuration properties include:
- **Database Connection**: For persisting order data in MySQL.
- **Azure Service Bus**: Configured to send stock update messages to the **Inventory Service**.

## Integration with Other Services
- **Product Service**: Communicates synchronously to verify product availability during order creation.
- **Inventory Service**: Sends messages via **Azure Service Bus** to reduce stock asynchronously after an order is placed.

## Security
- **Role-Based Access Control**: Ensures only authenticated users and administrators can access endpoints.
- **JWT Authentication**: Secures communication and restricts access to authorized users.

The **Order Service** is essential for managing the ecommerce platform's order lifecycle, ensuring reliability and accuracy in order fulfillment.

