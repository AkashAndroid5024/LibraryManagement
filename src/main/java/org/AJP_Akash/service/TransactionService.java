package org.AJP_Akash.service;

import org.AJP_Akash.dao.BookDAO;
import org.AJP_Akash.dao.MemberDAO;
import org.AJP_Akash.dao.TransactionDAO;
import org.AJP_Akash.model.Book;
import org.AJP_Akash.model.Member;
import org.AJP_Akash.model.Transaction;
import java.util.List;
import java.time.LocalDateTime;


public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final BookService bookService = new BookService();
    private final MemberService memberService = new MemberService();

    // Issue a book
    public void issueBook(int bookId, int memberId) {
        try {
            Book book = bookService.getBookById(bookId);
            if (book == null || !book.isAvailability()) {
                throw new RuntimeException("Book is not available or doesn't exist.");
            }

            Member member = memberService.getMemberById(memberId);
            if (member == null) {
                throw new RuntimeException("Member does not exist.");
            }

            Transaction transaction = new Transaction(book, member, LocalDateTime.now());
            transactionDAO.addTransaction(transaction);
            bookService.updateAvailability(bookId, false);
            System.out.println("Book issued successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Return a book
    public void returnBook(int bookId, int memberId) {
        try {
            Book book = bookService.getBookById(bookId);
            Member member = memberService.getMemberById(memberId);
            if (book == null || member == null) {
                throw new RuntimeException("Invalid book or member ID.");
            }

            Transaction transaction = transactionDAO.getActiveTransactionForBookAndMember(bookId, memberId);
            if (transaction == null) {
                throw new RuntimeException("No matching issue record found.");
            }

            transaction.setReturnDate(LocalDateTime.now());
            transactionDAO.updateTransaction(transaction); // Persist the update
            bookService.updateAvailability(bookId, true);
            System.out.println("Book returned successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Show all transactions
    public void showAllTransactions() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction records:");
            for (Transaction transaction : transactions) {
                System.out.printf("Transaction ID: %d, Book Id: %d, Book: %s, Member Id: %d, Member: %s, Issue Date: %s, Return Date: %s%n",
                        transaction.getTransactionId(),
                        transaction.getBook().getBookId(),
                        transaction.getBook().getTitle(),
                        transaction.getMember().getMemberId(),
                        transaction.getMember().getName(),
                        transaction.getIssueDate(),
                        (transaction.getReturnDate() != null ? transaction.getReturnDate() : "Not Returned Yet"));
            }
        }
    }

    public List<Transaction> getTransactionsByBookId(int bookId) {
        // Retrieve all transactions related to a particular book
        // This could involve querying the database to get transactions where the book ID matches
        return transactionDAO.findByBookId(bookId);
    }

    public List<Transaction> getTransactionsByMemberId(int memberId) {
        // Retrieve all transactions related to a particular member
        // This could involve querying the database to get transactions where the member ID matches
        return transactionDAO.findByMemberId(memberId);
    }

    public void deleteTransaction(int transactionId) {
        // Delete the transaction by its ID
        transactionDAO.deleteById(transactionId);
    }
}
