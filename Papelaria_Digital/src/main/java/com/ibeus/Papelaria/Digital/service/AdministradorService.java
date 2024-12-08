package com.ibeus.Papelaria.Digital.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Administrador;
import com.ibeus.Papelaria.Digital.repository.AdministradorRepository;

@Service
public class AdministradorService implements UserDetailsService {

    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrador administrador = administradorRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador não encontrado com o nome de usuário: " + username));

        return User.builder()
                .username(administrador.getUsername())
                .password(administrador.getPassword())
                .roles("ADMIN")
                .build();
    }

    public Administrador criarAdministrador(Administrador administrador, PasswordEncoder passwordEncoder) {
        administrador.setPassword(passwordEncoder.encode(administrador.getPassword()));
        return administradorRepository.save(administrador);
    }

    public Optional<Administrador> buscarPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador atualizarAdministrador(Long id, Administrador administradorAtualizado, PasswordEncoder passwordEncoder) {
        return administradorRepository.findById(id).map(administrador -> {
            administrador.setUserName(administradorAtualizado.getUsername());
            administrador.setEmail(administradorAtualizado.getEmail());

            if (administradorAtualizado.getPassword() != null) {
                administrador.setPassword(passwordEncoder.encode(administradorAtualizado.getPassword()));
            }
            return administradorRepository.save(administrador);
        }).orElseThrow(() -> new RuntimeException("Administrador não encontrado com o ID: " + id));
    }

    public void deletarAdministrador(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new RuntimeException("Administrador não encontrado com o ID: " + id);
        }
        administradorRepository.deleteById(id);
    }
}