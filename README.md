# Auction System - Spring Boot Backend

A real-time auction system backend built with Spring Boot and MySQL. Users can create items for auction, place bids on items, and the system automatically manages the auction lifecycle and determines winners.

## Quick Links
- [Features](#features)
- [Clone Project](#clone-project)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Build & Run](#build--run)
- [API Endpoints](#api-endpoints)
- [Architecture](#architecture)

---

## Features

### 1. Item Management
- Create new auction items
- List all active auction items
- Get item details including current highest bid
- Retrieve winner of a closed auction

### 2. Bidding System
- Place bids on items
- Automatic validation:
    - Auction must be **OPEN**
    - Auction must not be expired
    - Bid must be higher than current highest bid

### 3.  Auction Lifecycle
- Automatic auction closing using Spring Scheduler (`@Scheduled`)
- Auctions automatically close after `auctionEndTime`

### 4. Data Persistence
- MySQL database
- Spring Data JPA repositories
- One-to-Many relationship between **Item** and **Bid**

### 5. Exception Handling
- Global exception handling using `@ControllerAdvice`
- Custom exceptions:
    - `ResourceNotFoundException`
    - `InvalidBidException`



## 6. Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Lombok**
- **Spring Scheduler**
- **RESTful APIs**

---

## Clone Project

### Step 1: Clone the Repository

```bash
# Clone the project
git clone https://github.com/DilshanMadhusanka/springboot-auction-system.git

# Navigate to project directory
cd springboot-auction-system
```

### Step 2: Verify Project Structure

```bash
# Check if project is cloned successfully
ls -la

# You should see:
# - src/ (source code)
# - pom.xml (Maven configuration)
# - README.md (this file)
```

---

## Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- **Maven 3.8.1** or higher ([Download](https://maven.apache.org/download.cgi))
- **MySQL 8.0** or higher ([Download](https://dev.mysql.com/downloads/mysql/))
- **Git** (for cloning the project)

### Verification

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check MySQL version
mysql --version
```

---

## Database Setup



### Step 1: Configure Database Connection

Edit the file: `src/main/resources/application.properties`

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/auction_system
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

**Important**: 
- Replace `YOUR_PASSWORD_HERE` with your MySQL root password
- Database name is set to `auction_system` (you can change it if you want, but make sure to update the URL accordingly)

### Step 2: Verify Database Connection

Tables will be created automatically when the application starts for the first time.

---

## Build & Run

### Build the Project

```bash
# Navigate to project directory
cd springboot-auction-system

# Clean and build
mvn clean install

# Expected output: BUILD SUCCESS
```

### Run the Application

```bash
# Run using Maven
mvn spring-boot:run

```

### Verify Application is Running

The application will start and be available at: `http://localhost:8082`

Check the logs for:
```
Started AuctionSystemApplication in X.XXX seconds
```

---

## API Endpoints

Base URL: `http://localhost:8080/api/v1`

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/items` | POST | Create new auction item |
| `/items` | GET | List all active items |
| `/items/{itemId}` | GET | Get item details |
| `/items/{itemId}/bids` | POST | Place a bid |
| `/items/{itemId}/winner` | GET | Get winner details |

### Example Requests

**Create Item**:
```bash
curl -X POST http://localhost:8080/api/v1/items \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Laptop",
    "description": "Gaming laptop",
    "startingPrice": 1000.00,
    "auctionEndTime": "2026-12-31T23:59:59"
  }'
```

**List Items**:
```bash
curl http://localhost:8080/api/v1/items
```

**Place Bid**:
```bash
curl -X POST http://localhost:8080/api/v1/items/1/bids \
  -H "Content-Type: application/json" \
  -d '{"bidderName":"John","bidAmount":1500}'
```

**Get Winner**:
```bash
curl http://localhost:8080/api/v1/items/1/winner
```

## Postman Collection

 - You can also import the following Postman collection to test all endpoints:
```
 https://documenter.getpostman.com/view/29627464/2sBXcEk12P
```

---

## Architecture

### Layered Design

```
Controllers (ItemController, BidController)
    ↓
Services (ItemService, BidService, AuctionScheduler)
    ↓
Repositories (ItemRepository, BidRepository)
    ↓
Database (MySQL)
```

### Key Components

- **ItemController**: Manages item creation and retrieval
- **BidController**: Handles bid placement
- **ItemService**: Business logic for items
- **BidService**: Bid validation and processing
- **AuctionScheduler**: Checks every 60 seconds; if an auction's `auctionEndTime` is less than the current time, it automatically updates the auction status to **CLOSED**
- **Item Entity**: Auction item with status (OPEN/CLOSED)
- **Bid Entity**: Individual bids on items

---

## Key Features

- **Real-time Bid Validation**: Rejects bids on expired auctions immediately
-  **Automatic Auction Closing**: Scheduler runs every 60 seconds
- **Status Management**: OPEN and CLOSED auction states
- **Error Handling**: Proper HTTP status codes and messages
- **Data Persistence**: JPA with MySQL
- **Concurrent Access**: Status checks prevent race conditions

---
## Concurrency Considerations 

### Currently
- System validates bid amount before saving.
- However, simultaneous bids may cause race conditions.

### In a Real Production System, I Would Use

- @Transactional
- Optimistic locking with @Version field in Item entity
- Pessimistic Locking (JPA LockModeType.PESSIMISTIC_WRITE)
- Database-level constraints

### This ensures

- Two users cannot override each other’s bids.
- Only one bid can be accepted as the highest at any given time.
 ---

### Database Connection Error
```
Solution:
1. Ensure MySQL is running
2. Check username/password in application.properties
3. Verify database exists: mysql -u root -p (then SHOW DATABASES;)
```

### Port 8082 Already in Use
```
Solution:
1. Change port in application.properties: server.port=8083
2. Or kill the process using port 8082
```

### Tables Not Created
```
Solution:
1. Check application.properties has: spring.jpa.hibernate.ddl-auto=update
2. Restart the application
```

---

