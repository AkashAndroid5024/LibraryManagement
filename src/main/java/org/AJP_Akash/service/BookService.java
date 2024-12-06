package org.AJP_Akash.service;



import org.AJP_Akash.dao.BookDAO;
import org.AJP_Akash.dao.TransactionDAO;
import org.AJP_Akash.model.Book;
import java.util.List;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    // Add a new book
    public void addBook(String title, String author, String genre) {
        try {

            Book book = new Book(title, author, genre);
            if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
                throw new Exception("Book title cannot be empty!");
            }
            if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
                throw new Exception("Book author cannot be empty!");
            }
            if (book.getGenre() == null || book.getGenre().trim().isEmpty()) {
                throw new Exception("Book genre cannot be empty!");
            }
            if (bookDAO.isDuplicateBook(book)) {
                throw new Exception("Duplicate book entry! A book with the same title, author, and genre already exists.");
            }
            bookDAO.addBook(book);
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Show all books
    public void showAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                System.out.printf("ID: %d, Title: %s, Author: %s, Genre: %s, Available: %b%n",
                        book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailability());
            }
        }
    }

    public void deleteBook(int bookId) {
        // Fetch the book from the database

        Book book = bookDAO.getBookById(bookId);
        book.getTransactions().size();
        if (book != null) {
            // Check if there are any active transactions
            if (book.getTransactions().isEmpty()) {
                bookDAO.deleteBook(bookId);  // This will delete the book and any related transactions due to cascading delete
                System.out.println("Book deleted permanently.");
            } else {
                System.out.println("Book cannot be deleted because it has active transactions.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }




    // Update availability
    public void updateAvailability(int bookId, boolean availability) {
        try {
            bookDAO.updateBookAvailability(bookId, availability);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Get a book by ID
    public Book getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }
    // Show all books, including deleted ones (for auditability)
    public void showAllBooksIncludingDeleted() {
        List<Book> books = bookDAO.getAllBooksIncludingDeleted();  // New DAO method to fetch all
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                System.out.printf("ID: %d, Title: %s, Author: %s, Genre: %s, Available: %b%n",
                        book.getBookId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailability());
            }
        }
    }

}
