package com.unison.monitoring.security;

import com.unison.monitoring.member.entity.MemberEntity;
import com.unison.monitoring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> optionalMember = memberRepository.findByIdAndIsActiveTrue(username);

        MemberEntity memberEntity = optionalMember.orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        return new UserDetailImpl(memberEntity);
    }
}
