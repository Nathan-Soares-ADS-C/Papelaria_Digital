package com.ibeus.Papelaria.Digital.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibeus.Papelaria.Digital.model.Administrador;
import com.ibeus.Papelaria.Digital.repository.AdministradorRepository;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorController(AdministradorRepository administradorRepository, PasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint para criar um novo administrador
    @PostMapping("/criar")
    public ResponseEntity<Administrador> criarAdministrador(@RequestBody Administrador administrador) {
        // Criptografa a senha antes de salvar
        administrador.setPassword(passwordEncoder.encode(administrador.getPassword()));
        Administrador novoAdministrador = administradorRepository.save(administrador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAdministrador);
    }

    // Endpoint para listar todos os administradores
    @GetMapping
    public ResponseEntity<Iterable<Administrador>> listarAdministradores() {
        return ResponseEntity.ok(administradorRepository.findAll());
    }

    // Endpoint para buscar um administrador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Long id) {
        Optional<Administrador> administrador = administradorRepository.findById(id);
        return administrador.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para atualizar um administrador
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizarAdministrador(
            @PathVariable Long id,
            @RequestBody Administrador administradorAtualizado) {
        Optional<Administrador> administradorExistente = administradorRepository.findById(id);
        if (administradorExistente.isPresent()) {
            Administrador administrador = administradorExistente.get();
            administrador.setUserName(administradorAtualizado.getUsername());
            administrador.setEmail(administradorAtualizado.getEmail());

            // Atualiza a senha, se for fornecida
            if (administradorAtualizado.getPassword() != null) {
                administrador.setPassword(passwordEncoder.encode(administradorAtualizado.getPassword()));
            }

            Administrador administradorSalvo = administradorRepository.save(administrador);
            return ResponseEntity.ok(administradorSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para deletar um administrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdministrador(@PathVariable Long id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para autenticar um administrador (login)
    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody Administrador administrador) {
        Optional<Administrador> administradorExistente = administradorRepository.findByUserName(administrador.getUsername());

        if (administradorExistente.isPresent() &&
                passwordEncoder.matches(administrador.getPassword(), administradorExistente.get().getPassword())) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }
    }
}
