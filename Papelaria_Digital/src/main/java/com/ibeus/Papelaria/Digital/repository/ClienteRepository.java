package com.ibeus.Papelaria.Digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibeus.Papelaria.Digital.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar cliente pelo nome de usuário
    Optional<Cliente> findByUserName(String userName);
}
