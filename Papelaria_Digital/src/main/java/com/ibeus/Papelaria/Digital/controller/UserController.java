package com.ibeus.Papelaria.Digital.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Retorna usuário se encontrado
        } else {
            // Retorna 404 caso o usuário não seja encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }


    @PostMapping("/cliente")
    @Operation(summary = "Cria um usuário com o papel USER")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        return userService.saveUser(user);
    }

    @PostMapping("/admin")
    @Operation(summary = "Cria um usuário com o papel ADMIN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public User createUserAdmin(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Codificando a senha
        user.setRole("ADMIN");  // Definindo o papel como ADMIN

        return userService.saveUser(user);  // Salvando o usuário no banco de dados
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
