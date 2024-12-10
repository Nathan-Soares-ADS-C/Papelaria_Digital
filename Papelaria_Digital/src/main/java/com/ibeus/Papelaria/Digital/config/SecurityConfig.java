package com.ibeus.Papelaria.Digital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ibeus.Papelaria.Digital.repository.UserRepository;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/pedidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pedidos/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/pedidos/{id}/status").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                .requestMatchers("/users/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .httpBasic();

    return http.build();
}


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}