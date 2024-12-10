package com.ibeus.Papelaria.Digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibeus.Papelaria.Digital.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
