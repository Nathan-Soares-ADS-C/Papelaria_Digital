package com.ibeus.Papelaria.Digital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springdoc.core.customizers.OperationCustomizer;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customizeOperations() {
        return (operation, method) -> {
            // Obtém as credenciais de autenticação do usuário
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // Verifica se o usuário está autenticado
            if (auth != null && auth.isAuthenticated()) {
                // Verifica se o usuário tem a role ADMIN
                boolean hasAdminRole = auth.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

                // Se o usuário não for ADMIN e o endpoint tem a tag "Admin", o endpoint será ocultado
                if (operation.getTags() != null && operation.getTags().contains("Admin") && !hasAdminRole) {
                    return null;  // Retorna null para ocultar o endpoint na documentação
                }
            }

            // Retorna a operação caso o filtro não seja acionado
            return operation;
        };
    }
}
