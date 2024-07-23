package com.unison.monitoring.global.security;


import com.unison.monitoring.api.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDetailImpl implements UserDetails {

    private final Member member;

    public UserDetailImpl(Member member){
        this.member = member;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole()));

        return authorities;
    }

    @Override
    public String getPassword() {

        return Optional.ofNullable(member).map(Member::getPw).orElse("");
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(member).map(Member::getId).orElse("");

    }
}
