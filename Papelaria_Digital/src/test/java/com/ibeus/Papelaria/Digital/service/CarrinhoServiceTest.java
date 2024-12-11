package com.ibeus.Papelaria.Digital.service;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.repository.CarrinhoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarrinhoServiceTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private Carrinho carrinho;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Criação de produto com o que a classe Produto permite
        produto = new Produto();
        produto.setId(1L); // A classe Produto provavelmente tem um ID, como sua estrutura indicaria
        // Não estamos definindo nome diretamente, pois ele pode não ser acessível.

        // Criação do carrinho associado ao produto
        carrinho = new Carrinho();
        carrinho.setIdItem(1L);
        carrinho.setProduto(produto);  // Associando o produto ao carrinho
        carrinho.setQuantidade(2);
    }

    @Test
    void testGetAllCarrinhos() {
        List<Carrinho> carrinhos = List.of(carrinho, new Carrinho());
        when(carrinhoRepository.findAll()).thenReturn(carrinhos);

        List<Carrinho> result = carrinhoService.getAllCarrinhos();
        assertThat(result).hasSize(2);
        verify(carrinhoRepository, times(1)).findAll();
    }

    @Test
    void testGetCarrinhoById_Success() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        Carrinho result = carrinhoService.getCarrinhoById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getIdItem()).isEqualTo(1L);
    }

    @Test
    void testGetCarrinhoById_NotFound() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.empty());
        Carrinho result = carrinhoService.getCarrinhoById(1L);
        assertThat(result).isNull();
    }

    @Test
    void testCreateCarrinho() {
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);
        Carrinho result = carrinhoService.createCarrinho(carrinho);
        assertThat(result).isNotNull();
        verify(carrinhoRepository, times(1)).save(carrinho);
    }

    @Test
    void testUpdateCarrinho_Success() {
        Carrinho carrinhoDetails = new Carrinho();
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(2L); // Modificando o ID para simular uma mudança de produto
        carrinhoDetails.setProduto(produtoAtualizado);
        carrinhoDetails.setQuantidade(5);

        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(carrinhoRepository.save(carrinho)).thenReturn(carrinho);

        Carrinho result = carrinhoService.updateCarrinho(1L, carrinhoDetails);
        assertThat(result).isNotNull();
        assertThat(result.getProduto()).isEqualTo(produtoAtualizado);
        assertThat(result.getQuantidade()).isEqualTo(5);
        verify(carrinhoRepository, times(1)).save(carrinho);
    }

    @Test
    void testUpdateCarrinho_NotFound() {
        Carrinho carrinhoDetails = new Carrinho();
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(2L);
        carrinhoDetails.setProduto(produtoAtualizado);
        carrinhoDetails.setQuantidade(5);

        when(carrinhoRepository.findById(1L)).thenReturn(Optional.empty());

        Carrinho result = carrinhoService.updateCarrinho(1L, carrinhoDetails);
        assertThat(result).isNull();
    }

    @Test
    void testDeleteCarrinho_Success() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        doNothing().when(carrinhoRepository).deleteById(1L);

        boolean result = carrinhoService.deleteCarrinho(1L);
        assertThat(result).isTrue();
        verify(carrinhoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCarrinho_NotFound() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = carrinhoService.deleteCarrinho(1L);
        assertThat(result).isFalse();
        verify(carrinhoRepository, never()).deleteById(1L);
    }
}
