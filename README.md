# Bank-System

Bank System with Spring Boot
Introduction
This project implements a simple Bank System using Spring Boot. It demonstrates basic banking functionalities like creating accounts, transferring funds, and viewing account balances. The project is built using Java and Spring Boot, making it easy to run and deploy.

########################Features
Account Management: Create new accounts and view account details.
Transaction Handling: Transfer funds between accounts.
Balance Inquiry: Check account balances.
####################Technologies Used
Java
Spring Boot
Spring Data JPA
Spring Security
swagger-ui
Spring Mail
pdf
Database Mysql
###################Running the Application
Build the project: mvn clean install
Run the application: mvn spring-boot:run
The application will start on http://localhost:3000.
Creating an Account
To create a new account, send a POST request to /api/user with the following JSON payload:
{
    "firstName":"gomaa3",
    "lastName":"wahba",
    "otherName":"ahmed",
    "gender":"male",
    "address":"Cairo,BS,Egypt",
    "stateOfOrigine":"EGYPT",
    "email":"gomaawhba489@gmail.com",
    "password":"11111",
    "phoneNumber":"123456789",
    "alternativePhoneNumber":"123456789"

}
