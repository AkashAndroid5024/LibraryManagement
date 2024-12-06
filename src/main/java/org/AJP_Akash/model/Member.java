package org.AJP_Akash.model;

import javax.persistence.*;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)  // Add cascade remove here
    private Set<Transaction> transactions;  // Member is related to multiple transactions

    // Getters and setters

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Constructors
    public Member() {}

    public Member(String name, String email, String phoneNumber) throws Exception {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    // Getters and Setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email == null || !Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new Exception("Phone number must be exactly 10 digits!");
        }
        this.phoneNumber = phoneNumber;
    }


}
