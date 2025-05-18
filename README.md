# BankX Loan Service

---

## Introduction
You are tasked with building a Loan Payment System within a single Spring Boot application. The application should have two core domains:
1. Loan Domain: Manages loan creation and retrieval.
2. Payment Domain: Handles payments made towards loans.

The system should expose REST APIs for both domains, with each domain logically separated within the application. The application will use an in-memory H2 database for persistence.

## How to build and run

- To build the application run `./mvnw package` and then run the application through `java -jar target/bankx-loan-service-0.0.1-SNAPSHOT.jar` on the terminal from the root directory.
- To run on postman start the application and go to http://localhost:8080/swagger-ui/index.html
- Use the swagger definition to import into postman and run as tests.
