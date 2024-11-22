package com.unison.monitoring.api.member.service;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;

public interface MemberCRUDService {

     void createMember(ApiRequest<MemberDto.Request> request);
    Member getMemberById(String memberId);
    void updateMember(ApiRequest<MemberDto.Request> request) throws Exception;
    void deleteMemberById(String memberId);

}
