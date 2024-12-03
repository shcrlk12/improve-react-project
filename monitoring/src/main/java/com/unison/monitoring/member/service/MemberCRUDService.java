package com.unison.monitoring.api.member.service;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;

import java.util.List;

public interface MemberCRUDService {

     void createMember(ApiRequest<MemberDto.Request> request);
    MemberDto.Response getMemberById(String memberId) throws Exception;

    List<MemberDto.Response> getMembers() throws Exception;

    void updateMember(ApiRequest<MemberDto.Request> request) throws Exception;
    void deleteMemberById(String memberId) throws Exception;

}
