package com.example.apoorpoor_backend.config;

import com.example.apoorpoor_backend.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;


    //@Bean
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/user").hasRole("USER")
                                .anyRequest().authenticated());

        http
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedPage("/accessDenied"));

        http
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/").permitAll());

        http
                .oauth2Login((oauth2Login) -> oauth2Login
                        .loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

    }
}
