package com.unison.monitoring.api.member.service;

import com.unison.monitoring.api.member.model.Member;
import com.unison.monitoring.api.member.model.MemberEntity;

public interface MemberCRUDService {

     void createMember(Member member);
    Member getMemberById(String memberId);
    void updateMember(Member member);
    void deleteMemberById(String memberId);

}
