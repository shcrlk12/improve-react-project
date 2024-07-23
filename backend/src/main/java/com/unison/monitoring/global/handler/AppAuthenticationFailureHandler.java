package com.unison.monitoring.global.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.unison.monitoring.global.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.unison.monitoring.global.util.ErrorCodeMatcher.getErrorCode;

@Component
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();

        ErrorCode errorCode = getErrorCode(authException);

        data.put("error", errorCode.getCode());
        data.put("message", errorCode.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }

}
