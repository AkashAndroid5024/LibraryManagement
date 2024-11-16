package org.AJP_Akash;


import org.AJP_Akash.model.Book;
import org.AJP_Akash.model.Member;
import org.AJP_Akash.service.BookService;
import org.AJP_Akash.service.MemberService;
import org.AJP_Akash.service.TransactionService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        MemberService memberService = new MemberService();
        TransactionService transactionService = new TransactionService();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add a new book");
            System.out.println("2. Register a new member");
            System.out.println("3. Issue a book");
            System.out.println("4. Return a book");
            System.out.println("5. Show all books");
            System.out.println("6. Show all members");
            System.out.println("7. Show all transactions");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add a new book
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    bookService.addBook(book);

                    System.out.println("Book added successfully: " + book);
                    break;

                case 2:
                    // Register a new member
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter member email: ");
                    String email = scanner.nextLine();

                    Member member = new Member();
                    member.setName(name);
                    member.setEmail(email);
                    memberService.registerMember(member);

                    System.out.println("Member registered successfully: " + member);
                    break;

                case 3:
                    // Issue a book to a member
                    System.out.print("Enter book ID to issue: ");
                    int bookId = scanner.nextInt();
                    System.out.print("Enter member ID to issue the book to: ");
                    int memberId = scanner.nextInt();

                    transactionService.issueBook(bookId, memberId);
                    System.out.println("Book issued successfully.");
                    break;

                case 4:
                    // Return a book
                    System.out.print("Enter book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    System.out.print("Enter member ID who is returning the book: ");
                    int returnMemberId = scanner.nextInt();

                    transactionService.returnBook(returnBookId, returnMemberId);
                    System.out.println("Book returned successfully.");
                    break;

                case 5:
                    // Show all books
                    System.out.println("List of all books:");
                    bookService.getAllBooks().forEach(System.out::println);
                    break;

                case 6:
                    // Show all members
                    System.out.println("List of all members:");
                    memberService.getAllMembers().forEach(System.out::println);
                    break;

                case 7:
                    // Show all transactions
                    System.out.println("List of all transactions:");
                    transactionService.getAllTransactions().forEach(System.out::println);
                    break;

                case 8:
                    // Exit
                    System.out.println("Exiting the system...");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
