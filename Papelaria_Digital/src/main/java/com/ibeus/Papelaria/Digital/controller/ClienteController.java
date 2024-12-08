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

import com.ibeus.Papelaria.Digital.model.Cliente; // Mudado de Administrador para Cliente
import com.ibeus.Papelaria.Digital.repository.ClienteRepository; // Mudado de AdministradorRepository para ClienteRepository

@RestController
@RequestMapping("/api/clientes") // Rota alterada para "/api/clientes"
public class ClienteController {

    private final ClienteRepository clienteRepository; // Mudado de AdministradorRepository para ClienteRepository
    private final PasswordEncoder passwordEncoder;

    public ClienteController(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint para criar um novo cliente
    @PostMapping("/criar")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        // Criptografa a senha antes de salvar
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        Cliente novoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    // Endpoint para listar todos os clientes
    @GetMapping
    public ResponseEntity<Iterable<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    // Endpoint para buscar um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para atualizar um cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setUserName(clienteAtualizado.getUsername());
            cliente.setEmail(clienteAtualizado.getEmail());

            // Atualiza a senha, se for fornecida
            if (clienteAtualizado.getPassword() != null) {
                cliente.setPassword(passwordEncoder.encode(clienteAtualizado.getPassword()));
            }

            Cliente clienteSalvo = clienteRepository.save(cliente);
            return ResponseEntity.ok(clienteSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para deletar um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para autenticar um cliente (login)
    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findByUserName(cliente.getUsername());

        if (clienteExistente.isPresent() &&
                passwordEncoder.matches(cliente.getPassword(), clienteExistente.get().getPassword())) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }
    }
}
