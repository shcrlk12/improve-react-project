package com.unison.monitoring.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.unison.monitoring.api.entity.MemberEntity;
import com.unison.monitoring.api.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {
    }
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> json = new HashMap<>();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().toString();

        MemberEntity memberEntity = memberRepository.findById(userDetails.getUsername()).get();

        memberEntity.setLastLoginTime(LocalDateTime.now());

        Map<String, Object> data = new HashMap<>();
        data.put("id", userDetails.getUsername());
        data.put("type", "login");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("role", role.replaceAll("[\\[\\]]", ""));
        attributes.put("message", "로그인에 성공했습니다.");

        data.put("attributes", attributes);

        json.put("data", data);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().write(objectMapper.writeValueAsString(json));
    }
}