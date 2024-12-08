package com.ibeus.Papelaria.Digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Cliente;
import com.ibeus.Papelaria.Digital.repository.ClienteRepository;

@Service
public class ClienteService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o nome de usuário: " + username));
        return User.builder()
                .username(cliente.getUsername())
                .password(cliente.getPassword())
                .roles("USER") // Cliente tem o papel "USER"
                .build();
    }

    // Métodos CRUD
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente create(Cliente cliente) {
        // Aqui, você pode criptografar a senha, caso necessário
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
