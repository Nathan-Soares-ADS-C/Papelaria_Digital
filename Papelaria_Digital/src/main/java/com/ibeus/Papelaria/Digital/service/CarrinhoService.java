package com.ibeus.Papelaria.Digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.repository.CarrinhoRepository;
import com.ibeus.Papelaria.Digital.repository.ProdutoRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Carrinho> listarTodos() {
        return carrinhoRepository.findAll();
    }

    public Carrinho adicionarProdutoAoCarrinho(Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Carrinho carrinhoExistente = carrinhoRepository.findAll().stream()
                .filter(c -> c.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElse(null);

        if (carrinhoExistente != null) {
            carrinhoExistente.setQuantidade(carrinhoExistente.getQuantidade() + quantidade);
            return carrinhoRepository.save(carrinhoExistente);
        } else {
            Carrinho novoItem = new Carrinho();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(quantidade);
            return carrinhoRepository.save(novoItem);
        }
    }

    // Atualiza a quantidade de um item no carrinho
    public Carrinho atualizarQuantidade(Long idItem, Integer quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado!"));
        carrinho.setQuantidade(quantidade);
        return carrinhoRepository.save(carrinho);
    }

    // Deleta um item do carrinho
    public void deletarItem(Long idItem) {
        if (!carrinhoRepository.existsById(idItem)) {
            throw new RuntimeException("Item do carrinho não encontrado!");
        }
        carrinhoRepository.deleteById(idItem);
    }
}
