package org.AJP_Akash.dao;


import org.AJP_Akash.model.Member;
import org.AJP_Akash.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MemberDAO {
    // Add a new member
    public void addMember(Member member) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(member);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error adding member: " + e.getMessage());
        }
    }



    // Get member by ID
    public Member getMemberById(int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Member.class, memberId);
        }
    }

    // Mark member as deleted
    public void deleteMember(int memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Fetch the member
            Member member = session.get(Member.class, memberId);

            if (member != null) {
                // Delete transactions manually
                for (org.AJP_Akash.model.Transaction transaction : member.getTransactions()) {
                    if (transaction != null) {
                        session.delete(transaction);
                    }
                }

                // Now delete the member
                session.delete(member);

                tx.commit();
            } else {
                throw new RuntimeException("Member not found with ID: " + memberId);
            }
        }
    }


    // Get all members excluding deleted ones
    public List<Member> getAllMembers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Member where deleted = false", Member.class).list();
        }
    }

    // Get all members including deleted ones
    public List<Member> getAllMembersIncludingDeleted() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Member m", Member.class).list();
        }
    }



    public boolean isDuplicatePhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(m) FROM Member m WHERE m.phoneNumber = :phoneNumber";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("phoneNumber", phoneNumber);
            Long count = query.uniqueResult();
            return count != null && count > 0; // If count > 0, the phone number exists
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while checking for duplicate phone number.");
        }
    }



}
