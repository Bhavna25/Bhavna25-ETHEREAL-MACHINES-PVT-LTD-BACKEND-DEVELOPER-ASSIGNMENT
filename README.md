# Bhavna25-ETHEREAL-MACHINES-PVT-LTD-BACKEND-DEVELOPER-ASSIGNMENT

Part 1: Value Generation and Database Schema
1. For Value Generation
I have created a Java program(ValueGenerator.java) that generates values ​​for 20 machines on 5 axes, and inserts these values ​​into a database.
2. Database Schema(Part1.sql)

Part 2: User Management System with Spring Boot
1. Setting Up Spring Boot
Create a new Spring Boot project with dependencies for Spring Web, Spring Security, Spring Data JPA, and PostgreSQL.
2. Application Structure
Here’s a suggested directory structure for the application:
src/main/java/com/example/demo
    ├── controller
    │   └── UserController.java
    ├── model
    │   └── User.java
    ├── repository
    │   └── UserRepository.java
    ├── service
    │   └── UserService.java
    ├── security
    │   ├── JwtUtil.java
    │   ├── JwtRequestFilter.java
    │   └── WebSecurityConfig.java
    └── DemoApplication.java

3. User Management Code
4. JwtUtil.java (for token generation and validation)
5. WebSocket Implementation (Optional)
For real-time updates, consider using Spring WebSocket. Set up a WebSocket configuration and a message broker to push data to connected clients.

Final Steps
Build and Run the Application:
Use Maven or Gradle to build your project and run it locally.
Test Endpoints:
Use Postman or similar tools to test the RESTful API.

Github -
Open your terminal or command prompt.
Navigate to the directory where project is located.
Initialize a Git repository:
bash
git init

Add Project Files:
Add all project files to the repository:
git add .

Commit the files you've added:
git commit -m "Initial commit"

Connect to the Remote Repository:
Link local repository to the GitHub repository, just created:
git remote add origin https://github.com/yourusername/MachineValueGenerator.git

Push your commits to the remote repository:
git push -u origin master

