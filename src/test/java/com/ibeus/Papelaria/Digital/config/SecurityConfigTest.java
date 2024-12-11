package com.ibeus.Papelaria.Digital.config;

import com.ibeus.Papelaria.Digital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class SecurityConfigTest {

    @Mock
    private UserRepository userRepository;

    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        securityConfig = new SecurityConfig(userRepository);
    }

    @Test
    public void testSecurityFilterChain() throws Exception {
        // Testando se o SecurityFilterChain está sendo configurado corretamente.
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity);

        // Verifica se o objeto SecurityFilterChain não é nulo
        assertThat(filterChain).isNotNull();
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        // Testando a criação do AuthenticationManager
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        AuthenticationManager authenticationManager = securityConfig.authenticationManager(httpSecurity);

        // Verifica se o AuthenticationManager não é nulo
        assertThat(authenticationManager).isNotNull();
    }

    @Test
    public void testUserDetailsService() {
        // Testando o UserDetailsService
        UserDetailsService userDetailsService = securityConfig.userDetailsService();

        // Verifica se o UserDetailsService não é nulo
        assertThat(userDetailsService).isNotNull();
    }

    @Test
    public void testPasswordEncoder() {
        // Testando o PasswordEncoder
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Verifica se o PasswordEncoder não é nulo
        assertThat(passwordEncoder).isNotNull();
    }
}
