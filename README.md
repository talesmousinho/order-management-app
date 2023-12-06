# Project Name: Order Management System

This is a Spring Boot application for managing orders.

## Prerequisites

- Java 8
- PostgreSQL

## How to Run

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

## Note
Make sure that PostgreSQL is running on your machine before starting the application.