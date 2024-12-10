package com.ibeus.Papelaria.Digital.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.service.ProdutoService;

class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void testGetAllProdutos_Success() throws Exception {
        List<Produto> produtos = List.of(new Produto(), new Produto());
        when(produtoService.findAll()).thenReturn(produtos);

        mockMvc.perform(get("/api/produtos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(produtos.size()));
    }

    @Test
    void testGetProdutoById_Success() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.findById(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetProdutoById_NotFound() throws Exception {
        when(produtoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/produtos/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduto_Success() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.save(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Teste\", \"preco\": 10.0}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateProduto_Success() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoService.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoService.save(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Atualizado\", \"preco\": 15.0}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateProduto_NotFound() throws Exception {
        when(produtoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Atualizado\", \"preco\": 15.0}"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduto_Success() throws Exception {
        doNothing().when(produtoService).deleteById(1L);

        mockMvc.perform(delete("/api/produtos/1"))
               .andExpect(status().isNoContent());
    }
}
