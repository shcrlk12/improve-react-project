package com.unison.monitoring.common.security;


import com.unison.monitoring.common.properties.CorsProperties;
import com.unison.monitoring.common.filter.CookieAttributeFilter;
import com.unison.monitoring.common.handler.AppAccessDeniedHandler;
import com.unison.monitoring.common.handler.AppAuthenticationEntryPoint;
import com.unison.monitoring.common.handler.AppAuthenticationFailureHandler;
import com.unison.monitoring.common.handler.AppLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final UserDetailsService userDetailsService;
    private final CorsProperties corsProperties;

    private final AuthenticationSuccessHandler appAuthenticationSuccessHandler;
    private final AppAuthenticationFailureHandler appAuthenticationFailureHandler;
    private final AppLogoutSuccessHandler appLogoutSuccessHandler;
    private final AppAccessDeniedHandler appAppAccessDeniedHandler;
    private final AppAuthenticationEntryPoint appAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowedOrigins(Arrays.asList(
//                            String.format("%s://%s:%s", corsProperties.getProtocol(), corsProperties.getIp(), corsProperties.getPort()),
//                            String.format("%s://%s:%s", corsProperties.getProtocol(), corsProperties.getDomain(), corsProperties.getPort())
//                    ));
//                    config.setAllowedMethods(Collections.singletonList("*"));
//                    config.setAllowCredentials(true);
//                    config.setAllowedHeaders(Collections.singletonList("*"));
//                    config.setMaxAge(248461212L); //1시간
//                    return config;
//                }))
                .headers((headerConfig) ->
                        headerConfig
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/api/overview"),
                                        AntPathRequestMatcher.antMatcher("/api/users/**"),
                                        AntPathRequestMatcher.antMatcher("/api/data/**"),
                                        AntPathRequestMatcher.antMatcher("/api/reports/**"),
                                        AntPathRequestMatcher.antMatcher("/api/docx/**")
                                ).hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/api/login/**")
                                ).permitAll()
                                .anyRequest().authenticated()
                ).formLogin((formLogin) ->
                        formLogin
                                .loginPage("/api/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(appAuthenticationSuccessHandler)
                                .failureHandler(appAuthenticationFailureHandler)
                                .permitAll()
                )
                .logout((logout) ->
                        logout.deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutUrl("/api/logout")
                                .logoutSuccessHandler(appLogoutSuccessHandler)
                ).exceptionHandling((handling) ->
                        handling
                                .authenticationEntryPoint(appAuthenticationEntryPoint)
                                .accessDeniedHandler(appAppAccessDeniedHandler)
                )
                .addFilterBefore(cookieAttributeFilter(), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService);
        ;

        return http.build();
    }

    @Bean
    CookieAttributeFilter cookieAttributeFilter(){
        return new CookieAttributeFilter();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web
                        .ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        );
    }

    //암호화 기능
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}