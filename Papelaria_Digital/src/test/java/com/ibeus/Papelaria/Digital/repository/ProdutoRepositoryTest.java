package com.ibeus.Papelaria.Digital.repository;

import com.ibeus.Papelaria.Digital.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void testSaveAndFindById() {
        Produto produto = new Produto();
        produto.setName("Produto Teste");
        produto.setDescription("Descrição do Produto Teste");
        produto.setPrice(99.99);
        produtoRepository.save(produto);

        Produto foundProduto = produtoRepository.findById(produto.getId()).orElse(null);
        assertThat(foundProduto).isNotNull();
        assertThat(foundProduto.getName()).isEqualTo(produto.getName());
        assertThat(foundProduto.getDescription()).isEqualTo(produto.getDescription());
        assertThat(foundProduto.getPrice()).isEqualTo(produto.getPrice());
    }
}
