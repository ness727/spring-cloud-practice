package com.megamaker.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.userservice.mapper.UserMapper;
import com.megamaker.userservice.vo.RequestLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        //RequestLogin creds = UserMapper.INSTANCE.toRequestLogin(request.getInputStream());
        RequestLogin creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        RequestLogin creds = new RequestLogin();
//
//        Map<String, String> paramMap = new HashMap();
//        request.getParameterNames().asIterator().forEachRemaining(n -> paramMap.put(n, request.getParameter(n)));
//
//        creds.setEmail(paramMap.get("email"));
//        creds.setPassword(paramMap.get("password"));

        log.info("{}, {}", creds.getEmail(), creds.getPassword());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getEmail(),
                        creds.getPassword(),
                        Set.of(new SimpleGrantedAuthority("User"))
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

    }
}
