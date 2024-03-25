package com.megamaker.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.userservice.service.UserDto;
import com.megamaker.userservice.service.UserService;
import com.megamaker.userservice.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final Environment environment;

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

        //log.info("{}, {}", creds.getEmail(), creds.getPassword());

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
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByEmail(username);

        //byte[] secret = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());

//        String secretKey = Jwts.builder()
//                .setSubject(userDto.getUserId())
//                .setExpiration(new Date(System.currentTimeMillis() +
//                        Long.parseLong(environment.getProperty("token.expiration_time"))))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();

        Key secretKey = new SecretKeySpec(Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes()),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        log.info(environment.getProperty("token.secret"));
        String token = Jwts.builder()
                .subject(userDto.getUserId())
                .expiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(secretKey)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
    }
}
