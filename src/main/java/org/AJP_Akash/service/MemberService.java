package org.AJP_Akash.service;



import org.AJP_Akash.dao.MemberDAO;
import org.AJP_Akash.model.Member;
import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    public void registerMember(Member member) {
        memberDAO.saveMember(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }
}

