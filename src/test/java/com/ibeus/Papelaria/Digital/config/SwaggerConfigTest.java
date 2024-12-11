package com.ibeus.Papelaria.Digital.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springdoc.core.customizers.OperationCustomizer;
import io.swagger.v3.oas.models.Operation;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SwaggerConfigTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCustomizeOperations_AdminRole() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        SwaggerConfig swaggerConfig = new SwaggerConfig();
        OperationCustomizer customizer = swaggerConfig.customizeOperations();

        Operation operation = mock(Operation.class);
        when(operation.getTags()).thenReturn(List.of("Admin"));

        assertThat(customizer.customize(operation, null)).isNotNull();
    }

    @Test
    void testCustomizeOperations_UserRole() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SwaggerConfig swaggerConfig = new SwaggerConfig();
        OperationCustomizer customizer = swaggerConfig.customizeOperations();

        Operation operation = mock(Operation.class);
        when(operation.getTags()).thenReturn(List.of("Admin"));

        assertThat(customizer.customize(operation, null)).isNull();
    }

    @Test
    void testCustomizeOperations_Unauthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        SwaggerConfig swaggerConfig = new SwaggerConfig();
        OperationCustomizer customizer = swaggerConfig.customizeOperations();

        Operation operation = mock(Operation.class);

        assertThat(customizer.customize(operation, null)).isNotNull();
    }
}
