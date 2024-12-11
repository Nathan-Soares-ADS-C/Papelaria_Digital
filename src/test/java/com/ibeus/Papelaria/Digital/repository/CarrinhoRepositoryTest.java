package com.ibeus.Papelaria.Digital.repository;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarrinhoRepositoryTest {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @BeforeEach
    void setUp() {
        carrinhoRepository.deleteAll(); // Limpa os dados antes de cada teste
    }

    @Test
    void saveCarrinho_ShouldPersistCarrinho() {
        User user = new User();
        user.setId(1L); // Simula o ID do usuário existente

        Produto produto = new Produto();
        produto.setId(1L); // Simula o ID do produto existente

        Carrinho carrinho = new Carrinho();
        carrinho.setUser(user);
        carrinho.setProduto(produto);
        carrinho.setQuantidade(2);

        Carrinho savedCarrinho = carrinhoRepository.save(carrinho);

        assertThat(savedCarrinho).isNotNull();
        assertThat(savedCarrinho.getIdItem()).isNotNull();
        assertThat(savedCarrinho.getQuantidade()).isEqualTo(2);
    }

    @Test
    void findAll_ShouldReturnAllCarrinhos() {
        User user = new User();
        user.setId(1L);

        Produto produto1 = new Produto();
        produto1.setId(1L);

        Produto produto2 = new Produto();
        produto2.setId(2L);

        Carrinho carrinho1 = new Carrinho();
        carrinho1.setUser(user);
        carrinho1.setProduto(produto1);
        carrinho1.setQuantidade(1);

        Carrinho carrinho2 = new Carrinho();
        carrinho2.setUser(user);
        carrinho2.setProduto(produto2);
        carrinho2.setQuantidade(3);

        carrinhoRepository.save(carrinho1);
        carrinhoRepository.save(carrinho2);

        List<Carrinho> carrinhos = carrinhoRepository.findAll();

        assertThat(carrinhos).hasSize(2);
    }

    @Test
    void deleteCarrinho_ShouldRemoveCarrinho() {
        User user = new User();
        user.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);

        Carrinho carrinho = new Carrinho();
        carrinho.setUser(user);
        carrinho.setProduto(produto);
        carrinho.setQuantidade(1);

        Carrinho savedCarrinho = carrinhoRepository.save(carrinho);

        carrinhoRepository.deleteById(savedCarrinho.getIdItem());

        assertThat(carrinhoRepository.findById(savedCarrinho.getIdItem())).isEmpty();
    }

    @Test
    void findCarrinhosByUser_ShouldReturnCarrinhosForUser() {
        // Criando usuário e produtos
        User user = new User();
        user.setId(1L);

        Produto produto1 = new Produto();
        produto1.setId(1L);

        Produto produto2 = new Produto();
        produto2.setId(2L);

        Carrinho carrinho1 = new Carrinho();
        carrinho1.setUser(user);
        carrinho1.setProduto(produto1);
        carrinho1.setQuantidade(2);

        Carrinho carrinho2 = new Carrinho();
        carrinho2.setUser(user);
        carrinho2.setProduto(produto2);
        carrinho2.setQuantidade(1);

        // Salvando carrinhos
        carrinhoRepository.save(carrinho1);
        carrinhoRepository.save(carrinho2);

        // Buscando todos os carrinhos e filtrando pelo ID do usuário
        List<Carrinho> carrinhos = carrinhoRepository.findAll().stream()
                .filter(carrinho -> carrinho.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        // Verificando se retornou dois carrinhos para o usuário
        assertThat(carrinhos).hasSize(2);
    }

    @Test
    void findCarrinhosByUser_ShouldReturnEmptyIfNoCarrinhoFound() {
        User user = new User();
        user.setId(2L);  // ID de um usuário sem carrinhos

        // Buscando todos os carrinhos e filtrando pelo ID do usuário
        List<Carrinho> carrinhos = carrinhoRepository.findAll().stream()
                .filter(carrinho -> carrinho.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        // Verificando se não encontrou carrinhos
        assertThat(carrinhos).isEmpty();
    }
}
