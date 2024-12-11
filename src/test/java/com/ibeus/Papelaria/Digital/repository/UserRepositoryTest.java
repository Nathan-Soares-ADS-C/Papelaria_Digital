package com.ibeus.Papelaria.Digital.repository;
import com.ibeus.Papelaria.Digital.model.User;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;


import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Configuração do usuário antes de cada teste
        user = new User();
    }

    @Test
    void testGetAuthoritiesForAdminUser() {
        // Configura o usuário como ADMIN
        user.setRole("ADMIN");

        // Obtém as autoridades do usuário
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Verifica se o usuário tem as authorities corretas para ADMIN
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(2, authorities.size());
    }

    @Test
    void testGetAuthoritiesForRegularUser() {
        // Configura o usuário como USER
        user.setRole("USER");

        // Obtém as autoridades do usuário
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Verifica se o usuário tem apenas a authority ROLE_USER
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(1, authorities.size());
    }

    @Test
    void testGetUsername() {
        // Configura o login do usuário
        user.setLogin("testuser");

        // Verifica se o nome de usuário está correto
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        // Verifica se o usuário está com a conta não expirada
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Verifica se o usuário está com a conta não bloqueada
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Verifica se as credenciais do usuário não expiraram
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Verifica se o usuário está habilitado
        assertTrue(user.isEnabled());
    }

    @Test
    void testEqualsAndHashCode() {
        // Cria dois usuários com o mesmo id
        User user1 = new User(1L, "testuser", "password", "USER");
        User user2 = new User(1L, "testuser", "password", "USER");

        // Verifica se os dois usuários são iguais e possuem o mesmo hashCode
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
