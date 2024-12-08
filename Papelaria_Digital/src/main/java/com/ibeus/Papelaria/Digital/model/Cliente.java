package com.ibeus.Papelaria.Digital.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String password;

    private String email;

    private String endereco;

    @ElementCollection(fetch = FetchType.EAGER) // Coletando as permissões diretamente
    private List<String> roles; // Lista de roles associadas ao cliente

    public void atualizarEndereco(String novoEndereco) {
        this.endereco = novoEndereco;
    }

    public void trocarSenha(String novaSenha) {
        this.password = novaSenha;
    }

    // Métodos da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converter roles em objetos GrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Defina a lógica conforme sua necessidade
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Defina a lógica conforme sua necessidade
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Defina a lógica conforme sua necessidade
    }

    @Override
    public boolean isEnabled() {
        return true; // Defina a lógica conforme sua necessidade
    }
}
