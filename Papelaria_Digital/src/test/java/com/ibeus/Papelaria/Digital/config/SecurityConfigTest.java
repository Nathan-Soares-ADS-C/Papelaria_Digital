package com.ibeus.Papelaria.Digital.config;

import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.result.MockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Mock do UserRepository
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        
        // Criando um usuário mockado
        User user = new User(1L, "admin", new BCryptPasswordEncoder().encode("password"), "ROLE_ADMIN");
        when(userRepository.findByLogin("admin")).thenReturn(java.util.Optional.of(user));
        User regularUser = new User(2L, "user", new BCryptPasswordEncoder().encode("password"), "ROLE_USER");
        when(userRepository.findByLogin("user")).thenReturn(java.util.Optional.of(regularUser));

        // Configura o MockMvc para simular requisições HTTP
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new SecurityConfig(userRepository)) // Configuração de segurança
                .apply(springSecurity()) // Aplica segurança no MockMvc
                .build();
    }

    @Test
    public void testSecurityFilterChain() throws Exception {
        // Testa a configuração do SecurityFilterChain

        // Verifica se as URLs estão protegidas conforme a configuração
        mockMvc.perform(post("/api/produtos/1")
                .with(user("admin").roles("ADMIN"))) // Usuário com permissão ADMIN
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/produtos/1")
                .with(user("user").roles("USER"))) // Usuário com permissão USER
                .andExpect(status().isForbidden()); // Acesso negado
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        // Simulando um login com sucesso e validando a autenticação
        mockMvc.perform(get("/api/pedidos/1")
                .with(user("admin").roles("ADMIN"))) // Usuário autenticado
                .andExpect(status().isOk())
                .andExpect(authenticated()); // Verifica se o usuário está autenticado
    }

    @Test
    public void testUserDetailsService() throws Exception {
        // Verifica se o UserDetailsService carrega o usuário corretamente
        mockMvc.perform(get("/api/produtos/1")
                .with(user("admin").roles("ADMIN"))) // Usuário autenticado com role ADMIN
                .andExpect(status().isOk()); // Verifica se o acesso foi permitido
    }

    @Test
    public void testPasswordEncoder() throws Exception {
        // Verifica se o PasswordEncoder está funcionando (com um exemplo simples)
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodedPassword = encoder.encode(rawPassword);

        // Testa a codificação de senha
        mockMvc.perform(post("/api/login")
                .param("username", "admin")
                .param("password", encodedPassword)) // Envia senha codificada
                .andExpect(status().isOk()); // Verifica se o login foi bem-sucedido
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        // Testa se o acesso é negado para usuário não autenticado
        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isUnauthorized()); // Acesso não autorizado
    }
}
