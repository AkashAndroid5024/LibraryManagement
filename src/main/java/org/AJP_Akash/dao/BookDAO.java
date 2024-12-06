package org.AJP_Akash.dao;

import org.AJP_Akash.model.Book;
import org.AJP_Akash.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class BookDAO {
    // Add a new book
    public void addBook(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error adding book: " + e.getMessage());
        }
    }



    // Get book by ID
    public Book getBookById(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, bookId);
        }
    }

    // Update book availability
    public void updateBookAvailability(int bookId, boolean availability) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                book.setAvailability(availability);
                session.update(book);
                tx.commit();
            } else {
                throw new RuntimeException("Book not found with ID: " + bookId);
            }
        }
    }


    // Mark book as deleted
    public void deleteBook(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                session.delete(book);
                tx.commit();
            } else {
                throw new RuntimeException("Book not found with ID: " + bookId);
            }
        }
    }

    // Get all books excluding deleted ones
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book where deleted = false", Book.class).list();
        }
    }

    // Get all books including deleted ones
    public List<Book> getAllBooksIncludingDeleted() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book b", Book.class).list();
        }
    }



    public boolean isDuplicateBook(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(b) FROM Book b WHERE b.title = :title AND b.author = :author AND b.genre = :genre";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("title", book.getTitle());
            query.setParameter("author", book.getAuthor());
            query.setParameter("genre", book.getGenre());
            Long count = query.uniqueResult();
            return count != null && count > 0; // If count > 0, the book already exists
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while checking duplicate book: " + e.getMessage());
        }
    }


}
