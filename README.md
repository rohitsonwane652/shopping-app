# Shopping Rewards API

A Spring Boot application to calculate and retrieve reward points for customers based on their purchase transactions.
---
## Reward Calculation Logic
- Customers receive **2 points** for every dollar spent **over $100** in each transaction.
- Customers receive **1 point** for every dollar spent **between $50 and $100** in each transaction.
- No points are awarded for spending below $50.

## Description
This project provides RESTful API endpoints built with Spring Boot to calculate and retrieve reward points earned by customers based on their transaction history. It supports generating both complete reward statements and statements limited to the last three months.
- The API endpoints are defined in the RewardController class, adhering to standard REST API design principles.
- The core business logic resides in the RewardServiceImpl class, which implements the RewardService interface to ensure loose coupling and maintainability.
- For fetching data logic TransactionRepository contains methods.
- Exception handling is centralized in the GlobalExceptionHandler class, which also manages custom exceptions like          TransactionNotFoundException,InvalidDataException and NotFoundException.
- Unit tests are implemented in the RewardsServiceImplTest and RewardControllerTest classes to ensure the correctness of business logic and API behavior.


## 📁 Project Structure
```text
src/
├── main/
│ ├── java/
│ │ └── com.shopping/
│ │ ├── controller/ # REST API controllers
│ │ │ └── RewardController.java
│ │ ├── exception/ # Custom exception handling
│ │ │ ├── GlobalExceptionHandler.java
| | | ├── InvalidDataException.java
| | | ├── NotFoundException.java
│ │ │ └── TransactionNotFoundException.java
│ │ ├── model/ # Data models/entities
│ │ ├── repository/ # Spring Data JPA interfaces
│ │ │ └── TransactionRepository.java
│ │ ├── service/ # Service layer
│ │ │ ├── RewardService.java
│ │ │ └── RewardServiceImpl.java
│ │ ├── utils/ # Utility classes
│ │ │ └── LogMessage.java
│ │ └── ShoppingAppApplication.java
│ └── resources/
│ └── application.properties # Spring configuration
└── test/ # Unit and integration tests
|
|
|
```

---

## API Endpoint

### Get Reward Points for a Customer

**Endpoint:**  
`1.GET /rewards/user/{customerId}`

**Path Variable:**
- `customerId` (Long) – Unique ID of the customer

**Description:**  
Returns the reward points accumulated by a customer over the **last 3 months** based on transaction amount.

E.g. /rewards/user/{501}
```json
{
    "data": {
        "customerId": 501,
        "monthlyPoints": {
            "APRIL 2025": 91,
            "MAY 2025": 70
        },
        "totalPoints": 161
    },
    "message": "Successfully fetched reward points for customerId:501",
    "error": null,
    "errorDesc": null,
    "url": null,
    "totalRecords": 1,
    "errorType": null
}
```

`2.GET /rewards/get-reward-statement`
**Description:**  
Returns the reward points for all customers based on transaction amount.

E.g. /rewards/get-reward-statement
```json
{
    "data": [
        {
            "customerId": 501,
            "monthlyPoints": {
                "APRIL 2025": 91,
                "MAY 2025": 70
            },
            "totalPoints": 161
        },
        {
            "customerId": 502,
            "monthlyPoints": {
                "MARCH 2025": 311
            },
            "totalPoints": 311
        }
    ],
    "message": "Successfully fetched reward points for all customers",
    "error": null,
    "errorDesc": null,
    "url": null,
    "totalRecords": 2,
    "errorType": null
}
```



## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL
- JUnit / Mockito

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/rohitsonwane652/shopping-app.git
   cd shopping-app
2. **Build the project:**
   ```bash
   mvn clean install
3. **Run the aplication:**
   ```bash
    mvn spring-boot:run



