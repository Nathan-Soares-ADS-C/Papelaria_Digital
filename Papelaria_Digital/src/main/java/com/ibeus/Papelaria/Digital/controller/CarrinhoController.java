package com.ibeus.Papelaria.Digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
@CrossOrigin(origins = "http://localhost:4200")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public List<Carrinho> listarCarrinho() {
        return carrinhoService.listarTodos();
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Carrinho> adicionarPrato(
            @RequestParam Long pratoId, 
            @RequestParam Integer quantidade
    ) {
        Carrinho novoItem = carrinhoService.adicionarProdutoAoCarrinho(pratoId, quantidade);
        return ResponseEntity.ok(novoItem);
    }

    @PutMapping("/atualizar-quantidade/{idItem}")
    public ResponseEntity<Carrinho> atualizarQuantidade(
            @PathVariable Long idItem, 
            @RequestParam Integer quantidade
    ) {
        Carrinho atualizado = carrinhoService.atualizarQuantidade(idItem, quantidade);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{idItem}")
    public ResponseEntity<Void> deletarPrato(@PathVariable Long idItem) {
        carrinhoService.deletarItem(idItem);
        return ResponseEntity.noContent().build();
    }
}
