package com.ibeus.Papelaria.Digital.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.repository.ProdutoRepository;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto();
        produto.setId(1L);
    }

    @Test
    void testFindAll() {
        List<Produto> produtos = List.of(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.findAll();
        assertEquals(1, result.size());
        assertEquals("Produto Teste", result.get(0).getId());
    }

    @Test
    void testFindById_Success() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> foundProduto = produtoService.findById(1L);
        assertTrue(foundProduto.isPresent());
        assertEquals("Produto Teste", foundProduto.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Produto> foundProduto = produtoService.findById(1L);
        assertFalse(foundProduto.isPresent());
    }

    @Test
    void testSave() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto savedProduto = produtoService.save(produto);
        assertNotNull(savedProduto);
        assertEquals("Produto Teste", savedProduto.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deleteById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
