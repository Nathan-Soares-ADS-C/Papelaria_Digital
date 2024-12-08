package com.ibeus.Papelaria.Digital.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes") // Modificado o nome da tabela para "clientes"
@Data
public class Cliente implements UserDetails { // Renomeada a classe de "Administrador" para "Cliente"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Schema(description = "Nome de usuário do cliente", example = "clienteUser") // Alterado o nome para "clienteUser"
    private String userName;

    @Schema(description = "Senha do cliente", example = "clientePassword") // Alterado o nome para "clientePassword"
    private String password;

    @Schema(description = "Email do cliente", example = "cliente@gmail.com") // Alterado o nome para "cliente@gmail.com"
    private String email;

    public void trocarSenha(String novaSenha) {
        this.password = novaSenha;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Pode ser alterado conforme os requisitos de permissões do cliente
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
