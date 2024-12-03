package com.unison.monitoring.api.mapper;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;
import com.unison.monitoring.api.entity.MemberEntity;

import java.util.List;


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

    public static MemberDto.Response memberToMemberDto(MemberEntity member){

        return MemberDto.Response.builder()
                .id(member.getId())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }

    public static List<MemberDto.Response> memberToMemberDto(List<MemberEntity> members){


        return members.stream()
                .map(MemberMapper::memberToMemberDto)
                .toList();
    }

    public static MemberDto.Response memberToMemberDto(Member member){

        return MemberDto.Response.builder()
                .name(member.getName())
                .role(member.getRole())
                .build();
    }


}
