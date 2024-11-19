package com.unison.monitoring.api.member.service;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;

public interface MemberCRUDService {

     void createMember(ApiRequest<MemberDto.Request> member);
    Member getMemberById(String memberId);
    void updateMember(Member member);
    void deleteMemberById(String memberId);

}
