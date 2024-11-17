# Library Management System 📚

A **Library Management System** built using **Java**, **Hibernate**, and **MySQL**, designed to efficiently manage library operations such as adding books, registering members, issuing books, and returning books.

---

## Features 🚀

1. **Add New Books**: Maintain a catalog of books in the library.
2. **Register New Members**: Track library members and their details.
3. **Issue Books**: Allow members to borrow books from the library.
4. **Return Books**: Manage the return process and update the availability of books.
5. **View All Books**: Display all books in the library with their details.
6. **View All Members**: Display all registered members and their information.
7. **View All Transactions**: Show the history of all book issues and returns.

---

## Technologies Used 🛠️

### Backend
- **Java**: Core programming language for business logic and operations.
- **Hibernate**: ORM framework for mapping Java objects to database tables.
- **MySQL**: Relational database management system for storing data.

### Architecture
- **Model-View-Controller (MVC)**: 
  - **Model**: Represents the entities like `Book`, `Member`, and `Transaction`.
  - **DAO Layer**: Handles database operations using Hibernate.
  - **Service Layer**: Implements business logic for issuing/returning books and managing entities.
  - **UI**: Console-based, menu-driven interface for user interaction.

---

## Project Structure 🗂️
src/main/java/org/AJP_Akash/ ├── model/ # Entity classes (Book, Member, Transaction) 
                             ├── dao/ # Data Access Objects (DAO) for database operations 
                             ├── service/ # Business logic and service classes 
                             ├── config/ # Hibernate utility configuration 
                             ├── dto/ # Data Transfer Objects (if applicable) 
                             └── App.java # Main class for running the application

---

## How to Run the Project ▶️

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
                             
2.  **Set up the database:**

Install MySQL and create a database named library.
Run the following SQL script to create the necessary tables:

```
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    author VARCHAR(100),
    genre VARCHAR(50),
    available BOOLEAN
);

CREATE TABLE members (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    member_id INT,
    issueDate DATE,
    returnDate DATE,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);
```

3.  **Configure Hibernate:**

Update the hibernate.cfg.xml file with your MySQL credentials:
```
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/library</property>
<property name="hibernate.connection.username">your-username</property>
<property name="hibernate.connection.password">your-password</property>
```
4.  **Run the application:**

Compile and run the App.java file using your favorite IDE or terminal:
```
javac App.java
java App
Interact with the menu:
```
5.  **Interact with the menu:**

```
Library Management System Menu:
1. Add a new book
2. Register a new member
3. Issue a book
4. Return a book
5. Show all books
6. Show all members
7. Show all transactions
8. Exit
```
