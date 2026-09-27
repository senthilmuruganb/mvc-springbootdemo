# MVCDemo (mvc-springbootdemo)

## Overview

A Spring Boot MVC application implementing a login screen and a full CRUD (Create, Read, Update, Delete) workflow for a "User" entity. The project follows a layered architecture: Controller, Service, and Repository, with the Repository layer using plain JDBC (no Spring Data / JPA) against PostgreSQL.

## Technology Stack

- Java 17
- Spring Boot 3.3.4
- Spring Web (`spring-boot-starter-web`)
- Thymeleaf (`spring-boot-starter-thymeleaf`)
- PostgreSQL JDBC Driver 42.7.3
- Maven (with Maven Wrapper)

## Project Structure

```
mvc-springbootdemo
├── pom.xml
├── mvnw / mvnw.cmd
└── src
    ├── main
    │   ├── java/com/example/demo
    │   │   ├── MvcDemoApplication.java            Application entry point
    │   │   ├── controller/LoginController.java    Handles all HTTP routes
    │   │   ├── service/LoginService.java           Business logic layer
    │   │   ├── repository/LoginRepository.java     JDBC data access layer
    │   │   └── model/User.java                     User data model
    │   └── resources
    │       ├── application.properties
    │       └── templates
    │           ├── login.html
    │           ├── welcome.html
    │           ├── userlist.html
    │           ├── user-form.html
    │           └── user-edit-form.html
    └── test/java/com/example/demo
        └── MvcDemoApplicationTests.java            Default context-load test
```

## Prerequisites

- JDK 17 or later
- A running PostgreSQL server
- A database named `academics` reachable at `localhost:5432`

## Database Setup

`LoginRepository.java` connects to the database with the following hardcoded values:

```
URL:      jdbc:postgresql://localhost:5432/academics
User:     postgres
Password: admin
```

To use different credentials or a different database, edit these constants directly in `LoginRepository.java` (they are not read from `application.properties`).

The repository does not include a database schema. Based on the queries in `LoginRepository.java`, the application requires a `userdata` table shaped as follows:

```sql
CREATE TABLE public.userdata (
    userid    VARCHAR(20) PRIMARY KEY,
    username  VARCHAR(50),
    age       INTEGER,
    email     VARCHAR(100),
    password  VARCHAR(50)
);
```

Insert at least one row before testing login, for example:

```sql
INSERT INTO public.userdata (userid, username, age, email, password)
VALUES ('U001', 'Test User', 25, 'test@example.com', 'password123');
```

## Cloning and Running

```
git clone https://github.com/senthilmuruganb/mvc-springbootdemo.git
cd mvc-springbootdemo
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run` instead.

Once started, open:

```
http://localhost:8080/
```

This redirects to the login page. Log in using the `email` and `password` of a row in the `userdata` table.

## Application Walkthrough

`LoginController` exposes the following routes:

| Method | Path | Description |
|---|---|---|
| GET | `/` and `/login` | Displays the login form |
| POST | `/login` | Validates the submitted email and password against the database |
| GET | `/users` | Lists all users |
| GET | `/users/new` | Displays the form to add a new user |
| POST | `/users/save` | Saves a new user and redirects to the list |
| GET | `/users/edit/{id}` | Displays the form to edit an existing user |
| POST | `/users/update` | Updates a user and redirects to the list |
| GET | `/users/delete/{id}` | Deletes a user and redirects to the list |

`LoginController` delegates to `LoginService`, which in turn delegates to `LoginRepository` for all database access. `LoginRepository` uses `PreparedStatement` for all queries.

## Configuration

`application.properties` only sets the application name (`spring.application.name=MVCDemo`); the server runs on the Spring Boot default port, 8080.

## Notes

- Passwords are stored and compared as plain text in the current implementation; there is no hashing (for example, BCrypt) applied. This is acceptable for a learning exercise but should not be carried over into a production application.
- Database credentials are hardcoded in `LoginRepository.java` rather than externalized to `application.properties`.
