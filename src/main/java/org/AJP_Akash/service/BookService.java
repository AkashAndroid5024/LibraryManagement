package org.AJP_Akash.service;



import org.AJP_Akash.dao.BookDAO;
import org.AJP_Akash.model.Book;
import java.util.List;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    public void addBook(Book book) {
        bookDAO.saveBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public void updateBookAvailability(int bookId, boolean availability) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            book.setAvailable(availability);
            bookDAO.updateBook(book);
        }
    }
}

