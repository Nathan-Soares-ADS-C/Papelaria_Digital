package com.ibeus.Papelaria.Digital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibeus.Papelaria.Digital.model.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    // Método para buscar os itens do carrinho pelo userId do cliente
    List<Carrinho> findByClienteUserId(Long userId);
}
