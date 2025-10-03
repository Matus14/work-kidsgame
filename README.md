# Kids Math Quiz - Backend API

This is the backend part of the **Kids Math Quiz** application, a simple educational game that allows children to practice math problems. 
The backend is responsible for storing quiz results, retrieving leaderboard data, and managing player performance.


## Features

- REST API built with Spring Boot
- Stores quiz results in a MySQL database
- Automatically records timestamps of quiz completion
- Returns all stored results or top results for leaderboard
- Uses standard Java best practices (DTO version)
- Modular service & repository layer
- CORS enabled for integration with frontend (React/Vite)


## Technologies Used

- Java 17+
- Spring Boot (Web, Data JPA, Validation)
- Hibernate + MySQL
- Lombok
- Git & GitHub
- JUnit 5, Mockito, AssertJ (unit testing)


##  Learning Objectives

- Spring Boot REST API development
- JPA & Hibernate with MySQL
- Dependency Injection with `@Autowired`
- Layered architecture (Controller / Service / Repository/ Entity/ DTO)
- Entity annotations (`@Entity`, `@Id`, `@GeneratedValue`)
- Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- Spring Configuration (`application.properties`)
- Git & GitHub version control in a real project setup
- Create JUnit tests to validate Service class:
  - Happy path (create, findAll, findById, getTopResults, delete, getResults with paging)
  - Bad path (invalid input, entity not found, delete non-existing ID)
  - Using Mockito (mocking repository), AssertJ (assertions), Captor (argument verification)


##  Project Structure

src
├─ main
│  ├─ java/com/mathematics/kidsgame
│  │  ├─ controller
│  │  │  └─ QuizResultController.java
│  │  ├─ dto
│  │  │  ├─ QuizResultRequestDTO.java
│  │  │  └─ QuizResultResponseDTO.java
│  │  ├─ entity
│  │  │  └─ QuizResult.java
│  │  ├─ repository
│  │  │  └─ QuizResultRepository.java
│  │  ├─ service
│  │  │  ├─ QuizResultInterface.java
│  │  │  └─ QuizResultService.java
│  │  └─ KidsgameApplication.java
│  └─ resources
│     └─ application.properties
└─ test
├─ java/com/mathematics/kidsgame
│  └─ service
│     └─ QuizResultServiceTest.java
└─ resources

## API Endpoints
| Method | Endpoint                  | Description                                |
|--------|---------------------------|--------------------------------------------|
| POST   | `/api/results`            | Save new quiz result                       |
| GET    | `/api/results/all`        | Retrieve all quiz results (sorted by score)|
| GET    | `/api/results/top`        | Retrieve top 10 results for leaderboard    |
| GET    | `/api/results/{id}`       | Retrieve quiz result by ID                 |
| GET    | `/api/results?name=xyz&page=0&size=10` | Retrieve paged results (optional filter by player name) |
| DELETE | `/api/results/{id}`       | Delete quiz result by ID                   |


## AUTHOR ##

Created by **Matúš Bučko** as part of a full-stack portfolio project "Kids Math Quiz". 
This backend module was developed using technologies learned during a web development bootcamp, including **Spring Boot**, **MySQL**, **REST API**, and **JPA/Hibernate**.





