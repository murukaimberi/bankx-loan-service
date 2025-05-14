Technical Assessment: Loan Payment System (Single Application)
Overview
You are tasked with building a Loan Payment System within a single Spring Boot application. The application should have two core domains:
1.
Loan Domain: Manages loan creation and retrieval.
2.
Payment Domain: Handles payments made towards loans.
The system should expose REST APIs for both domains, with each domain logically separated within the application. The application will use an in-memory H2 database for persistence.
Requirements
1.
Loan Domain:
o
Implement the following API endpoint:
▪
POST /loans: Creates a new loan with a specified loan amount and term.
▪
GET /loans/{loanId}: Retrieves the details of a loan by its ID (loan amount, remaining balance, etc.).
2.
Payment Domain:
o
Implement the following API endpoint:
▪
POST /payments: Records a payment made towards a loan by providing the loanId and payment amount.
3.
Business Logic:
o
A loan must have an initial amount and term.
o
Payments should reduce the loan balance. If the payment exceeds the remaining balance, raise an error.
o
If a loan is settled in full, moved it to a SETTLED state.
4.
Separation Across Domains:
o
The Loan and Payment domains should be logically separated. For example, you can separate them into different packages, but you are free to structure your application as you see fit, as long as the separation of concerns is clear.
5.
Database
o
The application should use an H2 in-memory database for persistence.
6.
Unit Testing:
o
Write unit tests to ensure that:
▪
Loans are created successfully.
▪
Payments are processed and correctly reduce the loan balance.
7.
Documentation:
A product of
o
Provide a README.md file that includes:
▪
Instructions on how to build and run the application.
▪
How to test the APIs (using Postman, curl, or similar tools).
▪
Any relevant setup or configuration instructions.
Instructions
1. Set Up the Project
   •
   Create a GitHub repository for your solution.
   •
   The repository should include the following:
   o
   Spring Boot application with the two domains (Loan and Payment).
   o
   Unit tests for the key functionality.
2. Implement the Loan Domain
   •
   Loan Entity: Create an entity for Loan with the following fields:
   o
   loanId (unique identifier for the loan)
   o
   loanAmount (the total amount of the loan)
   o
   term (the duration of the loan in months)
   o
   status (ACTIVE or SETTLED, depending on whether the loan has been repaid in full)
   •
   Loan Controller: Implement a REST controller with the following endpoints:
   o
   POST /loans: To create a new loan.
   o
   GET /loans/{loanId}: To retrieve loan details.
3. Implement the Payment Domain
   •
   Payment Entity: Create an entity for Payment with the following fields:
   o
   paymentId (unique identifier for the payment)
   o
   loanId (the loan associated with the payment)
   o
   paymentAmount (the amount paid towards the loan)
   •
   Payment Controller: Implement a REST controller with the following endpoint:
   o
   POST /payments: To record a payment for a loan.
4. Database
   •
   The application should use an H2 in-memory database for persistence.
5. Unit Testing
   •
   Write unit tests to verify:
   o
   A loan is successfully created and stored in the database.
   o
   A payment is successfully recorded and reduces the loan balance.
   o
   Overpayment results in an error being raised.
   o
   If a loan is paid in full, it moves to a SETTLED state.
   A product of
6. Documentation
   •
   Update the README.md file with instructions on
   o
   How to build and run the application.
   o
   How to test the APIs (using Postman or curl).
   o
   Any additional configuration or setup notes.
   Submission
   •
   Create a new GitHub repository for your solution and commit the code there.
   •
   Include the necessary documentation and instructions in the README.md file.
   •
   Share the link to your GitHub repository with us once completed.
   Good luck, and we look forward to reviewing your solution!