# Shopping Rewards API

A Spring Boot application to calculate and retrieve reward points for customers based on their last 3 months purchase transactions.
---

## Reward Calculation Logic
- Customers receive **2 points** for every dollar spent **over $100** in each transaction.
- Customers receive **1 point** for every dollar spent **between $50 and $100** in each transaction.
- No points are awarded for spending below $50.


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
│ │ │ └── NotFoundException.java
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
`GET /rewards/user/{customerId}`

**Path Variable:**
- `customerId` (Long) – Unique ID of the customer

**Description:**  
Returns the reward points accumulated by a customer over the **last 3 months** based on transaction amount.

**Sample Response:**
```json
{
    "customerId": 501,
    "monthlyPoints": {
        "APRIL 2025": 91,
        "MAY 2025": 70
    },
    "totalPoints": 161
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
   ```
2. **Build the project:**
    ```bash
    mvn clean install
3. **Run the application:**
    ```bash
    mvn spring-boot:run


