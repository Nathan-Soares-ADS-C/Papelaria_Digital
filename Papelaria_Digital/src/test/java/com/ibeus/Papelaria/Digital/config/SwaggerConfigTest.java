package com.ibeus.Papelaria.Digital.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springdoc.core.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @Mock
    private Authentication authentication;

    @Mock
    private Operation operation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        swaggerConfig = new SwaggerConfig();
    }

    @Test
    void customizeOperations_ShouldHideAdminOperationForNonAdminUser() {
        // Simula um usuário autenticado com a role "ROLE_USER"
        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Criar uma operação com a tag "Admin"
        OperationCustomizer operationCustomizer = swaggerConfig.customizeOperations();
        when(operation.getTags()).thenReturn(List.of("Admin"));

        // Chama o método que deve ocultar a operação
        var result = operationCustomizer.apply(operation, null);

        // Verifica se a operação foi ocultada (deve retornar null)
        assertNull(result);
    }

    @Test
    void customizeOperations_ShouldShowAdminOperationForAdminUser() {
        // Simula um usuário autenticado com a role "ROLE_ADMIN"
        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Criar uma operação com a tag "Admin"
        OperationCustomizer operationCustomizer = swaggerConfig.customizeOperations();
        when(operation.getTags()).thenReturn(List.of("Admin"));

        // Chama o método que não deve ocultar a operação
        var result = operationCustomizer.apply(operation, null);

        // Verifica se a operação não foi ocultada (deve retornar a operação)
        assertNotNull(result);
    }

    @Test
    void customizeOperations_ShouldShowOperationForNonAdminUserWithoutAdminTag() {
        // Simula um usuário autenticado com a role "ROLE_USER"
        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Criar uma operação sem a tag "Admin"
        OperationCustomizer operationCustomizer = swaggerConfig.customizeOperations();
        when(operation.getTags()).thenReturn(List.of("Public"));

        // Chama o método que deve retornar a operação
        var result = operationCustomizer.apply(operation, null);

        // Verifica se a operação não foi modificada
        assertNotNull(result);
    }

    @Test
    void customizeOperations_ShouldHideAdminOperationForUnauthenticatedUser() {
        // Simula um usuário não autenticado
        SecurityContextHolder.clearContext();  // Limpa o contexto de autenticação

        // Criar uma operação com a tag "Admin"
        OperationCustomizer operationCustomizer = swaggerConfig.customizeOperations();
        when(operation.getTags()).thenReturn(List.of("Admin"));

        // Chama o método que deve ocultar a operação para usuário não autenticado
        var result = operationCustomizer.apply(operation, null);

        // Verifica se a operação foi ocultada
        assertNull(result);
    }

    @Test
    void customizeOperations_ShouldReturnNullIfOperationHasNoTags() {
        // Simula um usuário autenticado com a role "ROLE_USER"
        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Criar uma operação sem tags
        OperationCustomizer operationCustomizer = swaggerConfig.customizeOperations();
        when(operation.getTags()).thenReturn(null);

        // Chama o método que deve retornar a operação
        var result = operationCustomizer.apply(operation, null);

        // Verifica se a operação não foi modificada
        assertNotNull(result);
    }
}
