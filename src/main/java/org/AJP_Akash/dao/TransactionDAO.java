package org.AJP_Akash.dao;

import org.AJP_Akash.model.Transaction;
import org.AJP_Akash.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
//import org.hibernate.Transaction;

import java.util.List;

public class TransactionDAO {
    // Add a new transaction
    public void addTransaction(Transaction transaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.save(transaction);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error adding transaction: " + e.getMessage());
        }
    }

    // Update an existing transaction
    public void updateTransaction(Transaction transaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.update(transaction);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating transaction: " + e.getMessage());
        }
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Transaction", Transaction.class).list();
        }
    }

    // Get active transaction for a specific book and member
    public Transaction getActiveTransactionForBookAndMember(int bookId, int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Transaction t WHERE t.book.bookId = :bookId AND t.member.memberId = :memberId AND t.returnDate IS NULL";
            Query<Transaction> query = session.createQuery(hql, Transaction.class);
            query.setParameter("bookId", bookId);
            query.setParameter("memberId", memberId);
            return query.uniqueResult(); // Returns null if no matching record
        } catch (Exception e) {
            throw new RuntimeException("Error fetching active transaction: " + e.getMessage());
        }
    }

    // Check if a book is currently issued (i.e., an active transaction exists for the book)
    public boolean isBookIssued(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(t) FROM Transaction t WHERE t.book.bookId = :bookId AND t.returnDate IS NULL";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("bookId", bookId);
            long count = query.uniqueResult();
            return count > 0; // If the count is greater than 0, the book is issued and cannot be deleted
        }
    }

    // Check if a member has unreturned books
    public boolean hasUnreturnedBooks(int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(t) FROM Transaction t WHERE t.member.memberId = :memberId AND t.returnDate IS NULL";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("memberId", memberId);
            long count = query.uniqueResult();
            return count > 0; // If the count is greater than 0, the member has unreturned books and cannot be deleted
        }
    }

    // Find transactions by bookId
    public List<Transaction> findByBookId(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Transaction WHERE book.bookId = :bookId";
            Query<Transaction> query = session.createQuery(hql, Transaction.class);
            query.setParameter("bookId", bookId);
            return query.list();
        }
    }

    // Find transactions by memberId
    public List<Transaction> findByMemberId(int memberId) {
        // Correct HQL query to fetch transactions by member ID
        String hql = "FROM Transaction t WHERE t.member.id = :memberId";  // Adjust if the member field is different
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(hql, Transaction.class)
                    .setParameter("memberId", memberId)
                    .list();
        }
    }

    // Delete transaction by ID
    public void deleteById(int transactionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, transactionId);
            if (transaction != null) {
                session.delete(transaction);
            }
            tx.commit();
        }
    }



}
