package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(formLoginCustomizer -> formLoginCustomizer
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureHandler(new FormLoginAuthenticationFailureHandler()));

        http.logout(logoutCustomizer -> logoutCustomizer
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
