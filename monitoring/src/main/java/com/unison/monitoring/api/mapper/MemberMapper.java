package com.unison.monitoring.api.mapper;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;


public class MemberMapper {

    public static Member memberDtoToMember(ApiRequest<MemberDto.Request> memberRequest){
        MemberDto.Request memberDtoRequest = memberRequest.getData().getAttributes();

        return Member.builder()
                .id(memberRequest.getData().getId())
                .pw(memberDtoRequest.getPw())
                .name(memberDtoRequest.getName())
                .role(memberDtoRequest.getRole())
                .build();
    }

    public static MemberDto.Response memberToMemberDto(Member member){

        return MemberDto.Response.builder()
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
