package com.unison.monitoring.api.mapper;

import com.unison.common.dto.MemberDto;
import com.unison.monitoring.api.domain.Member;


public class MemberMapper {

    public static Member memberDtoToMember(MemberDto.Request memberDto){

        return Member.builder()
                .id(memberDto.getId())
                .pw(memberDto.getPw())
                .name(memberDto.getName())
                .role(memberDto.getRole())
                .build();
    }

    public static MemberDto.Response memberToMemberDto(Member member){

        return MemberDto.Response.builder()
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
