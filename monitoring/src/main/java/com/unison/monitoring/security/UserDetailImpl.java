package com.unison.monitoring.security;


import com.unison.monitoring.api.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserDetailImpl implements UserDetails {

    private final MemberEntity memberEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberEntity.getRole()));

        return authorities;
    }

    @Override
    public String getPassword() {

        return Optional.ofNullable(memberEntity).map(MemberEntity::getPw).orElse("");
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(memberEntity).map(MemberEntity::getId).orElse("");
    }

    public MemberEntity getMember(){
        return this.memberEntity;
    }
}
