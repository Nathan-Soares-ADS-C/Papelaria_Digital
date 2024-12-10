package com.ibeus.Papelaria.Digital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public Optional<User> findById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            // Log do erro
            System.err.println("Erro ao buscar usuário com ID " + id);
            e.printStackTrace();
            return Optional.empty(); // Retorna vazio em caso de erro
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
