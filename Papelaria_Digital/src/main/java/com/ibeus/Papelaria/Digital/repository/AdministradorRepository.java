package com.ibeus.Papelaria.Digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibeus.Papelaria.Digital.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByUserName(String userName);
}
