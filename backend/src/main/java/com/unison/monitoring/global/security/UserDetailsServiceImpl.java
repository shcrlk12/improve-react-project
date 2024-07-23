package com.unison.monitoring.global.security;

import com.unison.monitoring.api.member.Member;
import com.unison.monitoring.api.member.MemberRepository;
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
        Optional<Member> optionalMember = memberRepository.findById(username);

        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        memberRepository.save(member);

        return new UserDetailImpl(member);
    }
}
