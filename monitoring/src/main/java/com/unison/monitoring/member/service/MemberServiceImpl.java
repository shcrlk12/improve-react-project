package com.unison.monitoring.member.service;

import com.unison.common.dto.MemberDto;
import com.unison.common.jsonapi.request.ApiRequest;
import com.unison.monitoring.member.entity.MemberEntity;
import com.unison.monitoring.member.mapper.MemberMapper;
import com.unison.monitoring.member.repository.MemberRepository;
import com.unison.monitoring.common.security.UserDetailImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberCRUDService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createMember(ApiRequest<MemberDto.Request> request) throws RuntimeException{
        MemberDto.Request memberDto = request.getData().getAttributes();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

        memberRepository.save(MemberEntity.builder()
                .id(request.getData().getId())
                .pw(bCryptPasswordEncoder.encode(memberDto.getPw()))
                .role(memberDto.getRole())
                .name(memberDto.getName())
                .lastLoginTime(null)
                .isActive(true)
                .updatedBy(userDetail.getMember().getId())
                .createdAt(LocalDateTime.now())
                .createdBy(userDetail.getMember().getId())
                .build());
    }

    @Override
    public MemberDto.Response getMemberById(String memberId) throws Exception {

        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new Exception("not valid user id: " + memberId));

        return MemberMapper.toResponseDto(memberEntity);
    }

    @Override
    public List<MemberDto.Response> getMembers() throws Exception {

        List<MemberEntity> memberEntity = memberRepository.findByIsActiveTrue();

        return MemberMapper.toResponseDto(memberEntity);
    }

    @Override
    @Transactional
    public void updateMember(ApiRequest<MemberDto.Request> request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

        String userId = request.getData().getId();

        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new Exception("not found member id: " + request));

        memberEntity.updateMember(
                bCryptPasswordEncoder.encode(request.getData().getAttributes().getPw()),
                request.getData().getAttributes().getRole(),
                request.getData().getAttributes().getName(),
                userDetail.getMember().getId()
        );

        memberRepository.save(memberEntity);
    }

    @Override
    @Transactional
    public void deleteMemberById(String memberId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl)authentication.getPrincipal();

        MemberEntity memberEntity = memberRepository.findByIdAndIsActiveTrue(memberId)
                .orElseThrow(() -> new Exception("not found member id: " + memberId));

        memberEntity.deleteMember(userDetail.getMember().getId());

    }
}
