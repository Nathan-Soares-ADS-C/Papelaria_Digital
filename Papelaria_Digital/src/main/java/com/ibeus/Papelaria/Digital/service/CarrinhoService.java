package com.ibeus.Papelaria.Digital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.repository.CarrinhoRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public List<Carrinho> getAllCarrinhos() {
        return carrinhoRepository.findAll();
    }

    public Carrinho getCarrinhoById(Long id) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        return carrinho.orElse(null);
    }

    public Carrinho createCarrinho(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho updateCarrinho(Long id, Carrinho carrinhoDetails) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        if (optionalCarrinho.isPresent()) {
            Carrinho carrinho = optionalCarrinho.get();
            carrinho.setProduto(carrinhoDetails.getProduto());
            carrinho.setQuantidade(carrinhoDetails.getQuantidade());
            return carrinhoRepository.save(carrinho);
        } else {
            return null;
        }
    }

    public boolean deleteCarrinho(Long id) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        if (optionalCarrinho.isPresent()) {
            carrinhoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
}
