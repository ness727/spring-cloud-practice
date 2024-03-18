package com.megamaker.userservice.security;

import com.megamaker.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .userDetailsService(userService)
                .formLogin(f -> f
                        .defaultSuccessUrl("/welcome", true)
                        .usernameParameter("email")
                )
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(r -> r
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users"), antMatcher("/health_check"),
                                antMatcher("/welcome"), antMatcher("/login")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(getAuthenticationFilter())
                .getOrBuild();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment);
        authenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());

        return authenticationFilter;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

}
