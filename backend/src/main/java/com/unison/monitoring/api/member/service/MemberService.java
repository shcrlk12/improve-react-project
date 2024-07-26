package com.unison.monitoring.api.member.service;

import com.unison.monitoring.api.member.model.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements MemberCRUDService{
    @Override
    public void createMember(Member member) {

    }

    @Override
    public Member getMemberById(String memberId) {
        return null;
    }

    @Override
    public void updateMember(Member member) {

    }

    @Override
    public void deleteMemberById(String memberId) {

    }
}
