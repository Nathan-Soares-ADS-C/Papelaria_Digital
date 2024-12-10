package com.ibeus.Papelaria.Digital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Criando um usuário ADMIN padrão
            User user = new User();
            user.setLogin("admin");
            user.setPassword(passwordEncoder.encode("@123"));
            user.setRole("ADMIN");

            // Salvando o usuário no banco de dados
            userRepository.save(user);
        };
    }
}
