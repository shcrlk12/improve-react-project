package com.unison.monitoring.api.member.service;

import com.unison.monitoring.api.domain.Member;

public interface MemberCRUDService {

     void createMember(Member member);
    Member getMemberById(String memberId);
    void updateMember(Member member);
    void deleteMemberById(String memberId);

}
