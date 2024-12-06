package org.AJP_Akash;


import org.AJP_Akash.model.Book;
import org.AJP_Akash.model.Member;
import org.AJP_Akash.model.Transaction;
import org.AJP_Akash.service.BookService;
import org.AJP_Akash.service.MemberService;
import org.AJP_Akash.service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final BookService bookService = new BookService();
    private static final MemberService memberService = new MemberService();
    private static final TransactionService transactionService = new TransactionService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Library Management System!");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add a new book");
            System.out.println("2. Register a new member");
            System.out.println("3. Issue a book");
            System.out.println("4. Return a book");
            System.out.println("5. Show all books");
            System.out.println("6. Show all members");
            System.out.println("7. Show all transactions");
            System.out.println("8. Delete a book");
            System.out.println("9. Delete a member");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> addBook();
                case 2 -> registerMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> bookService.showAllBooksIncludingDeleted();
                case 6 -> memberService.showAllMembersIncludingDeleted();
                case 7 -> transactionService.showAllTransactions();
                case 8 -> deleteBook();
                case 9 -> deleteMember();
                case 10 -> {
                    System.out.println("Thank you for using the Library Management System!");
                    System.exit(0);
                    return;
                }
                default -> System.out.println("Invalid option. Please choose between 1 and 10.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();
        bookService.addBook(title, author, genre);
    }

    private static void registerMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member phone number: ");
        String phoneNumber = scanner.nextLine();
        memberService.registerMember(name, email, phoneNumber);
    }

    private static void issueBook() {
        try {
            System.out.print("Enter book ID to issue: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            transactionService.issueBook(bookId, memberId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric IDs.");
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter book ID to return: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            transactionService.returnBook(bookId, memberId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric IDs.");
        }
    }

    private static void deleteBook() {
        try {
            System.out.print("Enter book ID to delete: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            List<Transaction> transactions = transactionService.getTransactionsByBookId(bookId);

            // Loop through the transactions and delete completed transactions
            for (Transaction transaction : transactions) {
                if (transaction.getReturnDate() != null) {
                    // If the transaction is complete, delete it
                    transactionService.deleteTransaction(transaction.getTransactionId());
                }
            }
            bookService.deleteBook(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a numeric book ID.");
        }
    }

    private static void deleteMember() {
        try {
            System.out.print("Enter member ID to delete: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            List<Transaction> transactions = transactionService.getTransactionsByMemberId(memberId);

            // Loop through the transactions and delete completed transactions
            for (Transaction transaction : transactions) {
                if (transaction.getReturnDate() != null) {
                    // If the transaction is complete, delete it
                    transactionService.deleteTransaction(transaction.getTransactionId());
                    // Detach the transaction from the member's collection
                    transaction.getMember().getTransactions().remove(transaction);
                }
            }
            memberService.deleteMember(memberId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a numeric member ID.");
        }
    }

}
