# 🎬 Movie Library Cataloging System

## 🧾 Overview
The **Movie Library Cataloging System** is a Java-based mini project that allows users to manage and display a collection of movies.  
It demonstrates the integration of **Object-Oriented Programming**, **File Handling**, **Collections**, **JDBC**, and **JavaFX GUI** concepts.  
The project is divided into 5 modules, each representing one unit from the Java syllabus.

---

## 🧩 Project Modules

| Module | Java Unit | Concept | Description |
|---------|------------|----------|-------------|
| **mod1** | Unit I | OOP Concepts | Defines the `Movie` class with encapsulation, constructors, abstraction, and polymorphism. |
| **mod2** | Unit II | Exception Handling & Streams | Handles saving/loading of movie objects using Object Streams (`.dat` files). |
| **mod3** | Unit III | Generics & Collections | Manages a movie list using `ArrayList`, includes sorting and searching functionality. |
| **mod4** | Unit IV | JDBC & Database Connectivity | Connects to MySQL, retrieves and displays movie data using JDBC. |
| **mod5** | Unit V | GUI Programming (JavaFX) | Provides an interactive user interface for adding, updating, deleting, and viewing movies. |

---

## 🏗️ System Architecture

```plaintext
+-------------------------------+
|        JavaFX GUI (mod5)      |
|-------------------------------|
|       JDBC Connector (mod4)   |
|-------------------------------|
|   Movie Repository (mod3)     |
|-------------------------------|
|  File Handling System (mod2)  |
|-------------------------------|
|     Movie Class (mod1)        |
+-------------------------------+
## ⚙️ Technologies Used

- **Programming Language:** Java (JDK 17+ recommended)
- **IDE:** Eclipse / IntelliJ IDEA
- **Database:** MySQL
- **UI Framework:** JavaFX 21
- **Driver:** MySQL Connector/J (JDBC)
- **Build Tool:** Manual / Eclipse Build Path

---

## 🗄️ Database Setup

Run these SQL commands before executing the project:

```sql
CREATE DATABASE movie_catalog;
USE movie_catalog;

CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    director VARCHAR(100),
    year INT,
    genres VARCHAR(255)
);

INSERT INTO movies (title, director, year, genres)
VALUES ('Inception', 'Nolan', 2010, 'Sci-Fi'),
       ('Avatar', 'Cameron', 2009, 'Action'),
       ('Interstellar', 'Nolan', 2014, 'Sci-Fi');
