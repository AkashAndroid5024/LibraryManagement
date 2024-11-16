package org.AJP_Akash.service;

import org.AJP_Akash.dao.BookDAO;
import org.AJP_Akash.dao.MemberDAO;
import org.AJP_Akash.dao.TransactionDAO;
import org.AJP_Akash.model.Book;
import org.AJP_Akash.model.Member;
import org.AJP_Akash.model.Transaction;

import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO;
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;

    public TransactionService() {
        transactionDAO = new TransactionDAO();
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
    }

    // Issue a book to a member
    public void issueBook(int bookId, int memberId) {
        Book book = bookDAO.getBookById(bookId);
        Member member = memberDAO.getMemberById(memberId);

        if (book != null && member != null && book.isAvailable()) {
            // Create a transaction entry for issuing the book
            Transaction transaction = new Transaction();
            transaction.setBook(book);
            transaction.setMember(member);
            transaction.setIssueDate(new java.util.Date());

            // Save the transaction
            transactionDAO.issueBook(transaction);

            // Mark the book as not available
            book.setAvailable(false);
            bookDAO.updateBook(book);
        } else {
            System.out.println("Either the book or the member doesn't exist, or the book is already issued.");
        }
    }

    // Return a book
    public void returnBook(int bookId, int memberId) {
        Book book = bookDAO.getBookById(bookId);
        Member member = memberDAO.getMemberById(memberId);

        if (book != null && member != null) {
            // Get all transactions for the book and member
            List<Transaction> transactions = transactionDAO.getTransactionsForBookAndMember(bookId, memberId);

            boolean returned = false;

            // Process each transaction to find any that haven't been returned
            for (Transaction transaction : transactions) {
                if (transaction.getReturnDate() == null) { // Check if it's an open transaction
                    // Mark the transaction as returned
                    transaction.setReturnDate(new java.util.Date());
                    transactionDAO.updateTransaction(transaction);
                    returned = true;
                }
            }

            if (returned) {
                // Mark the book as available again if at least one transaction was returned
                book.setAvailable(true);
                bookDAO.updateBook(book);
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("No outstanding transaction found for this book and member.");
            }
        } else {
            System.out.println("Either the book or the member doesn't exist.");
        }
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getTransactions();
    }
}
