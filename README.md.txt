ToDoList Elite Solution
A secure, full-stack To-Do list application built with Spring Boot 3.x and MySQL. It features complete user authentication and persistence of user tasks.

✨ Key Features
Secure Auth: User registration and login using Spring Security.

Data Persistence: CRUD operations for tasks, backed by MySQL via Spring Data JPA.

Themed UI: Responsive design with a functional Dark Mode that saves user preference locally.

🛠️ Stack
Backend: Java 21, Spring Boot 3.x, Spring Security

Database: MySQL

Frontend: Thymeleaf, Custom CSS/JS

🚀 Quick Setup
1. Database Setup
Create a database named todorails in your local MySQL instance.

CREATE DATABASE todorails;

2. Configuration
Navigate to src/main/resources/.

Copy application.properties.example to application.properties.

In the new application.properties, update the placeholder for your local MySQL password and username.

3. Run
Run the main application class (FirstApplication.java) directly, or use Maven from the project root:

mvnw spring-boot:run

The application will start at http://localhost:8080/login.