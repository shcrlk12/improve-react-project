package com.unison.monitoring.api.member.service;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.api.domain.Member;
import com.unison.monitoring.api.entity.MemberEntity;
import com.unison.monitoring.api.member.MemberRepository;
import com.unison.monitoring.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberCRUDService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createMember(ApiRequest<MemberDto.Request> request) {
        MemberDto.Request memberDto = request.getData().getAttributes();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

        memberRepository.save(MemberEntity.builder()
                .id(request.getData().getId())
                .pw(bCryptPasswordEncoder.encode(memberDto.getPw()))
                .role(memberDto.getRole())
                .name(memberDto.getName())
                .lastLoginTime(null)
                .createdAt(LocalDateTime.now())
                .createdBy(userDetail.getMember().getId())
                .build());
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
