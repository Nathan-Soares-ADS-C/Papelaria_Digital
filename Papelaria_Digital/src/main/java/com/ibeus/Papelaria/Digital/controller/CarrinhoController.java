package com.ibeus.Papelaria.Digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public List<Carrinho> getAllCarrinhos() {
        return carrinhoService.getAllCarrinhos();
    }

    @PostMapping
    public Carrinho createCarrinho(@RequestBody Carrinho carrinho) {
        return carrinhoService.createCarrinho(carrinho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrinho> updateCarrinho(@PathVariable Long id, @RequestBody Carrinho carrinhoDetails) {
        Carrinho updatedCarrinho = carrinhoService.updateCarrinho(id, carrinhoDetails);
        if (updatedCarrinho != null) {
            return ResponseEntity.ok(updatedCarrinho);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Long id) {
        if (carrinhoService.deleteCarrinho(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
