# Kids Math Quiz - Backend API

This is the backend part of the **Kids Math Quiz** application, a simple educational game that allows children to practice math problems. 
The backend is responsible for storing quiz results, retrieving leaderboard data, and managing player performance.


## Features

- REST API built with Spring Boot
- Stores quiz results in a MySQL database
- Automatically records timestamps of quiz completion
- Returns all stored results or top results for leaderboard
- Uses standard Java best practices (DTO-free version)
- Modular service & repository layer
- CORS enabled for integration with frontend (React/Vite)


## Technologies Used

- Java 17+
- Spring Boot 3
- Spring Data JPA
- MySQL 
- Lombok
- Maven
- Git & GitHub


##  Learning Objectives

- Spring Boot REST API development
- JPA & Hibernate with MySQL
- Dependency Injection with `@Autowired`
- Layered architecture (Controller / Service / Repository)
- Entity annotations (`@Entity`, `@Id`, `@GeneratedValue`)
- Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- Spring Configuration (`application.properties`)
- Git & GitHub version control in a real project setup


##  Project Structure

src
├── main
│ ├── java/com/mathematics/kidsgame
│ │ ├── controller
│ │ │ └── QuizResultController.java
│ │ ├── entity
│ │ │ └── QuizResult.java
│ │ ├── repository
│ │ │ └── QuizResultRepository.java
│ │ ├── service
│ │ │ ├── QuizResultService.java
│ │ │ └── QuizResultServiceImpl.java
│ │ └── KidsgameApplication.java
│ └── resources
│ └── application.properties


## API Endpoints

| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| POST   | `/api/results`      | Save new quiz result                 |
| GET    | `/api/results`      | Retrieve all quiz results            |
| GET    | `/api/results/top`  | Retrieve top results for leaderboard |



## AUTHOR ##

Created by **Matúš Bučko** as part of a full-stack portfolio project "Kids Math Game". 
This backend module was developed using technologies learned during a web development bootcamp, including **Spring Boot**, **MySQL**, **REST API**, and **JPA/Hibernate**.





