package com.librarymanagement.service;

import com.librarymanagement.model.Member;

import java.util.ArrayList;

public class MemberService {

    private ArrayList<Member> members;

    public MemberService(){
        this.members = new ArrayList<>();
    }

    public void registerMember(Member member){
        members.add(member);
    }

    public Member findMemberById(int id) {
        for (Member m : members) {
            if (m.getPersonId() == id) {
                return m;
            }
        }
        return null;
    }

    public boolean removeMember(int id){
        Member member = findMemberById(id);
        if (member != null) {
            members.remove(member);
            return true;
        }
        return false;
    }

    public ArrayList<Member>listMembers(){
        return members;
    }
}
