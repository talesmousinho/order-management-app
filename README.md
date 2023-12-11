# Project: Order Management System

This project is a solution to a simple order management exercise. The goal was to develop an API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it.

## Index
- [Summary](#summary)
- [Features](#features)
- [Entities](#entities)
- [Requirements](#requirements)
- [Issues](#issues)
- [How to Run](#how-to-run)
- [API Endpoints](#api-endpoints)
  - [Item Controller](#item-controller)
  - [Order Controller](#order-controller)
  - [StockMovement Controller](#stockmovement-controller)
  - [User Controller](#user-controller)

## Summary

The Order Management System is a RESTful API designed to handle the lifecycle of orders in a simple inventory system. It allows users to create, read, update, and delete entities such as Items, Orders, Stock Movements, and Users.  
The system is designed to automatically fulfill orders based on the current stock and attribute stock movements to incomplete orders. It also provides a tracking feature to trace the list of stock movements used to complete an order and vice versa.  
Upon completion of an order, the system sends an email notification to the user who created the order. It also maintains a log file recording completed orders, stock movements, sent emails, and errors.  
The API is built using Java 8, Spring Boot, Spring JPA, PostgreSQL, GIT, and log4j. It is designed to be easy to set up and run, with detailed instructions provided in the "How to Run" section. 

## Features

The system provides the following features:

- Create, read, update, delete and list all entities.
- When an order is created, it tries to satisfy it with the current stock.
- When a stock movement is created, the system tries to attribute it to an incomplete order.
- When an order is complete, it sends a notification by email to the user that created it.
- Trace the list of stock movements that were used to complete the order, and vice-versa.
- Show current completion of each order.
- Write a log file with: orders completed, stock movements, email sent and errors.

## Entities

- Item
  - Name
- StockMovement
  - CreationDate
  - Item
  - Quantity
- Order
  - CreationDate
  - Item
  - Quantity
  - User
  - isCompleted
- User
  - Name
  - Email

## Requirements

The API is built with Java 8, Spring Boot, Spring JPA, PostgreSQL, GIT, and log4j.

Please refer to the "[How to Run](#how-to-run)" section for instructions on how to run the project and call the routes.

## Issues

The development of this project was organized into the following tasks, each represented by a GitHub issue:

1. [Set up Spring Boot project with required dependencies](https://github.com/talesmousinho/order-management-app/issues/1)
2. [Create JPA entities for Item, Order, StockMovement, and User](https://github.com/talesmousinho/order-management-app/issues/2)
3. [Implement CRUD endpoints for all entities](https://github.com/talesmousinho/order-management-app/issues/3)
4. [Implement order fulfillment logic](https://github.com/talesmousinho/order-management-app/issues/4)
5. [Attribute stock movements to pending orders](https://github.com/talesmousinho/order-management-app/issues/5)
6. [Send email notification upon order completion](https://github.com/talesmousinho/order-management-app/issues/6)
7. [Implement tracking of stock movements for orders](https://github.com/talesmousinho/order-management-app/issues/7)
8. [Show current completion status for each order](https://github.com/talesmousinho/order-management-app/issues/8)
9. [Set up logging for orders, stock movements, and email notifications](https://github.com/talesmousinho/order-management-app/issues/9)
10. [Document setup and API usage instructions](https://github.com/talesmousinho/order-management-app/issues/10)

## How to Run

### Prerequisites

- Java 8
- PostgreSQL

1. Clone the repository to your local machine.
```bash
git clone git@github.com:talesmousinho/order-management-app.git
```

2. Navigate to the project directory.
```bash
cd order-management-app/
```

3. Update the `application.properties` file with your PostgreSQL credentials.

4. Build the project using Maven.
```bash
./mvnw clean install
```

5. Run the Spring Boot application.
```bash
java -jar target/order-management-server-0.0.1-SNAPSHOT.jar
```

The application will start running at http://localhost:8000.

### Note
Make sure that PostgreSQL is running on your machine before starting the application.

## API Endpoints

### Item Controller

The `ItemController` provides endpoints for managing `Item` entities.

#### GET /api/v1/items

This endpoint retrieves all items.

Sample request:

```sh
curl -X GET http://localhost:8080/api/v1/items
```

Sample success response:
```json
[
  {
    "id": 1,
    "name": "Item 1"
  },
  {
    "id": 2,
    "name": "Item 2"
  }
]
```

#### GET /api/v1/items/{id}
This endpoint retrieves an item by its ID.

Sample request:

```sh
curl -X GET http://localhost:8080/api/v1/items/1
```


Sample success response:

```json
{
  "id": 1,
  "name": "Item 1"
}
```

#### POST /api/v1/items
This endpoint creates a new item.

Sample request:

```sh
curl -X POST -H "Content-Type: application/json" -d '{"name":"Item 1"}' http://localhost:8080/api/v1/items
```

Sample success response:

```json
{
  "id": 1,
  "name": "Item 1"
}
```

#### PUT /api/v1/items/{id}
This endpoint updates an existing item.

Sample request:

```sh
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Updated Item"}' http://localhost:8080/api/v1/items/1
```

Sample success response:
```json
{
  "id": 1,
  "name": "Updated Item"
}
```

#### DELETE /api/v1/items/{id}
This endpoint deletes an item by its ID.

Sample request:

```sh
curl -X DELETE http://localhost:8080/api/v1/items/1
```

Sample success response:

```json
HTTP/1.1 200 OK
```

### Order Controller

The `OrderController` provides endpoints for managing `Order` entities.

#### GET /api/v1/orders

This endpoint retrieves all orders.

Sample request:

```sh
curl -X GET http://localhost:8080/api/v1/orders
```

Sample success response:
```json
[
  {
    "id": 1,
    "item": {
      "id": 1,
      "name": "Item 1"
    },
    "quantity": 2,
    "user": {
      "id": 1,
      "name": "User 1",
      "email": "user1@example.com"
    }
  },
  // More orders...
]
```

#### GET /api/v1/orders/{id}
This endpoint retrieves an order by its ID.

Sample request:
```sh
curl -X GET http://localhost:8080/api/v1/orders/1
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 2,
  "user": {
    "id": 1,
    "name": "User 1",
    "email": "user1@example.com"
  }
}
```

#### POST /api/v1/orders

This endpoint creates a new order.

Sample request:

```sh
curl -X POST -H "Content-Type: application/json" -d '{"item":{"id":1},"quantity":2,"user":{"id":1}}' http://localhost:8080/api/v1/orders
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 2,
  "user": {
    "id": 1,
    "name": "User 1",
    "email": "user1@example.com"
  }
}
```

#### PUT /api/v1/orders/{id}
This endpoint updates an existing order.

Sample request:
```sh
curl -X PUT -H "Content-Type: application/json" -d '{"item":{"id":1},"quantity":3,"user":{"id":1}}' http://localhost:8080/api/v1/orders/1
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 3,
  "user": {
    "id": 1,
    "name": "User 1",
    "email": "user1@example.com"
  }
}
```

#### DELETE /api/v1/orders/{id}
This endpoint deletes an order by its ID.

Sample request:
```sh
curl -X DELETE http://localhost:8080/api/v1/orders/1
```

Sample success response:
```json
HTTP/1.1 200 OK
```

### StockMovement Controller

The `StockMovementController` provides endpoints for managing `StockMovement` entities.

#### GET /api/v1/stock-movements

This endpoint retrieves all stock movements.

Sample request:

```sh
curl -X GET http://localhost:8080/api/v1/stock-movements
```

Sample success response:
```json
[
  {
    "id": 1,
    "item": {
      "id": 1,
      "name": "Item 1"
    },
    "quantity": 10,
    "creationDate": "2022-01-01T00:00:00.000+00:00"
  },
  // More stock movements...
]
```

#### GET /api/v1/stock-movements/{id}
This endpoint retrieves a stock movement by its ID.

Sample request:
```sh
curl -X GET http://localhost:8080/api/v1/stock-movements/1
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 10,
  "creationDate": "2022-01-01T00:00:00.000+00:00"
}
```

#### POST /api/v1/stock-movements

This endpoint creates a new stock movement.

Sample request:
```sh
curl -X POST -H "Content-Type: application/json" -d '{"item":{"id":1},"quantity":10}' http://localhost:8080/api/v1/stock-movements
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 10,
  "creationDate": "2022-01-01T00:00:00.000+00:00"
}
```

#### PUT /api/v1/stock-movements/{id}
This endpoint updates an existing stock movement.

Sample request:
```sh
curl -X PUT -H "Content-Type: application/json" -d '{"item":{"id":1},"quantity":15}' http://localhost:8080/api/v1/stock-movements/1
```

Sample success response:
```json
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Item 1"
  },
  "quantity": 10,
  "creationDate": "2023-01-01T00:00:00.000+00:00"
}
```

#### DELETE /api/v1/stock-movements/{id}
This endpoint deletes a stock movement by its ID.

Sample request:
```sh
curl -X DELETE http://localhost:8080/api/v1/stock-movements/1
```

Sample success response:
```json
HTTP/1.1 200 OK
```

### User Controller

The `UserController` provides endpoints for managing `User` entities.

#### GET /api/v1/users

This endpoint retrieves all users.

Sample request:

```sh
curl -X GET http://localhost:8080/api/v1/users
```

Sample success response:
```json
[
  {
    "id": 1,
    "name": "User 1",
    "email": "user1@example.com"
  },
  // More users...
]
```

#### GET /api/v1/users/{id}
This endpoint retrieves a user by its ID.

Sample request:
```sh
curl -X GET http://localhost:8080/api/v1/users/1
```

Sample success response:
```json
{
  "id": 1,
  "name": "User 1",
  "email": "user1@example.com"
}
```

#### POST /api/v1/users

This endpoint creates a new user.

Sample request:

```sh
curl -X POST -H "Content-Type: application/json" -d '{"name":"User 1","email":"user1@example.com"}' http://localhost:8080/api/v1/users
```

Sample success response:
```json
{
  "id": 1,
  "name": "User 1",
  "email": "user1@example.com"
}
```

#### PUT /api/v1/users/{id}
This endpoint updates an existing user.

Sample request:
```sh
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Updated User","email":"user1@example.com"}' http://localhost:8080/api/v1/users/1
```

Sample success response:
```json
{
  "id": 1,
  "name": "Updated User",
  "email": "user1@example.com"
}
```

#### DELETE /api/v1/users/{id}
This endpoint deletes a user by its ID.

Sample request:
```sh
curl -X DELETE http://localhost:8080/api/v1/users/1
```

Sample success response:
```json
HTTP/1.1 200 OK
```