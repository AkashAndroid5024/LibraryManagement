package org.AJP_Akash.service;



import org.AJP_Akash.dao.MemberDAO;
import org.AJP_Akash.dao.TransactionDAO;
import org.AJP_Akash.model.Member;
import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    // Register a new member
    public void registerMember(String name, String email, String phoneNumber) {
        try {
            // Validate phone number before calling the method that checks for duplicates
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Phone number cannot be empty");
            }
            MemberDAO memberDAO1=new MemberDAO();
            // If phone number is valid, check for duplicates
            if (memberDAO1.isDuplicatePhone(phoneNumber)) {
                throw new Exception("Phone number is already registered");
            }

            // Proceed with member registration if validation passes
            MemberDAO memberDAO2=new MemberDAO();
            Member newMember = new Member(name, email, phoneNumber);
            memberDAO2.addMember(newMember);
            System.out.println("Member registered successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Show all members
    public void showAllMembers() {
        List<Member> members = memberDAO.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members are registered.");
        } else {
            System.out.println("Registered members:");
            for (Member member : members) {
                System.out.printf("ID: %d, Name: %s, Email: %s, Phone: %s%n",
                        member.getMemberId(), member.getName(), member.getEmail(), member.getPhoneNumber());
            }
        }
    }



    public void deleteMember(int memberId) {
        // Fetch the member from the database
        Member member = memberDAO.getMemberById(memberId);
        member.getTransactions().size();
        if (member != null) {
            // Check if there are any active transactions
            if (member.getTransactions().isEmpty()) {
                memberDAO.deleteMember(memberId);  // This will delete the member and any related transactions due to cascading delete
                System.out.println("Member deleted permanently.");
            } else {
                System.out.println("Member cannot be deleted because they have active transactions.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }




    // Get a member by ID
    public Member getMemberById(int memberId) {
        return memberDAO.getMemberById(memberId);
    }
    // Show all members, including deleted ones (for auditability)
    public void showAllMembersIncludingDeleted() {
        List<Member> members = memberDAO.getAllMembersIncludingDeleted();  // New DAO method to fetch all
        if (members.isEmpty()) {
            System.out.println("No members are registered.");
        } else {
            System.out.println("Registered members:");
            for (Member member : members) {
                System.out.printf("ID: %d, Name: %s, Email: %s, Phone: %s%n",
                        member.getMemberId(), member.getName(), member.getEmail(), member.getPhoneNumber());
            }
        }
    }

}
