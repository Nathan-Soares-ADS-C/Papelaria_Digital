package com.ibeus.Papelaria.Digital.repository;

import com.ibeus.Papelaria.Digital.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testSaveAndFindProduto() {
        // Criando um objeto Produto
        Produto produto = new Produto();
        produto.setName("Caneta");
        produto.setDescription("Caneta esferográfica azul");
        produto.setPrice(2.50);

        // Salvando o produto no banco de dados
        produto = produtoRepository.save(produto);

        // Verificando se o produto foi salvo corretamente
        assertNotNull(produto.getId());  // O ID não deve ser nulo após o salvamento
        assertEquals("Caneta", produto.getName());
        assertEquals("Caneta esferográfica azul", produto.getDescription());
        assertEquals(2.50, produto.getPrice());

        // Buscando o produto pelo ID
        Produto foundProduto = produtoRepository.findById(produto.getId()).orElse(null);

        // Verificando se o produto encontrado é o mesmo
        assertNotNull(foundProduto);
        assertEquals(produto.getId(), foundProduto.getId());
    }
}
