package com.unison.monitoring.global.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.unison.monitoring.api.member.Member;
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

        Map<String, Object> data = new HashMap<>();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().toString();

        Member member = memberRepository.findById(userDetails.getUsername()).get();

        member.setLastLoginTime(LocalDateTime.now());

        data.put("status", HttpServletResponse.SC_OK);
        data.put("username",  userDetails.getUsername());
        data.put("role", role.replaceAll("[\\[\\]]", ""));  // [ROLE_USER] 형식을 ROLE_USER로 변환
        data.put("message", "로그인에 성공했습니다.");

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().write(objectMapper.writeValueAsString(data));
    }
}