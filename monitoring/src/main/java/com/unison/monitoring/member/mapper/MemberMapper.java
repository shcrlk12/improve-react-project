package com.unison.monitoring.member.mapper;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.member.dto.Member;
import com.unison.monitoring.member.entity.MemberEntity;

import java.util.List;


public class MemberMapper {

    public static Member toMemberDto(ApiRequest<MemberDto.Request> memberRequest){
        MemberDto.Request memberDtoRequest = memberRequest.getData().getAttributes();

        return Member.builder()
                .id(memberRequest.getData().getId())
                .pw(memberDtoRequest.getPw())
                .name(memberDtoRequest.getName())
                .role(memberDtoRequest.getRole())
                .build();
    }

    public static MemberDto.Response toResponseDto(MemberEntity member){

        return MemberDto.Response.builder()
                .id(member.getId())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }

    public static List<MemberDto.Response> toResponseDto(List<MemberEntity> members){


        return members.stream()
                .map(MemberMapper::toResponseDto)
                .toList();
    }

    public static MemberDto.Response toResponseDto(Member member){

        return MemberDto.Response.builder()
                .name(member.getName())
                .role(member.getRole())
                .build();
    }


}
