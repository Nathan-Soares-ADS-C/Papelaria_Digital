package com.ibeus.Papelaria.Digital.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ibeus.Papelaria.Digital.model.Carrinho;
import com.ibeus.Papelaria.Digital.model.Produto;
import com.ibeus.Papelaria.Digital.model.User;
import com.ibeus.Papelaria.Digital.service.CarrinhoService;

class CarrinhoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarrinhoService carrinhoService;

    @InjectMocks
    private CarrinhoController carrinhoController;

    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carrinhoController).build();

        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        
        Produto produto = new Produto();
        produto.setId(1L);

        carrinho = new Carrinho();
        carrinho.setIdItem(1L);
        carrinho.setUser(user);
        carrinho.setProduto(produto);
        carrinho.setQuantidade(2);
    }

    @Test
    void testGetAllCarrinhos_Success() throws Exception {
        List<Carrinho> carrinhos = List.of(carrinho, new Carrinho());
        when(carrinhoService.getAllCarrinhos()).thenReturn(carrinhos);

        mockMvc.perform(get("/carrinho"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(carrinhos.size()));
    }

    @Test
    void testCreateCarrinho_Success() throws Exception {
        when(carrinhoService.createCarrinho(any(Carrinho.class))).thenReturn(carrinho);

        mockMvc.perform(post("/carrinho")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user\": {\"id\": 1}, \"produto\": {\"id\": 1}, \"quantidade\": 2}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.idItem").value(1L));
    }

    @Test
    void testUpdateCarrinho_Success() throws Exception {
        when(carrinhoService.updateCarrinho(eq(1L), any(Carrinho.class))).thenReturn(carrinho);

        mockMvc.perform(put("/carrinho/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user\": {\"id\": 1}, \"produto\": {\"id\": 1}, \"quantidade\": 3}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.idItem").value(1L));
    }

    @Test
    void testUpdateCarrinho_NotFound() throws Exception {
        when(carrinhoService.updateCarrinho(eq(1L), any(Carrinho.class))).thenReturn(null);

        mockMvc.perform(put("/carrinho/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user\": {\"id\": 1}, \"produto\": {\"id\": 1}, \"quantidade\": 3}"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCarrinho_Success() throws Exception {
        when(carrinhoService.deleteCarrinho(1L)).thenReturn(true);

        mockMvc.perform(delete("/carrinho/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCarrinho_NotFound() throws Exception {
        when(carrinhoService.deleteCarrinho(1L)).thenReturn(false);

        mockMvc.perform(delete("/carrinho/1"))
               .andExpect(status().isNotFound());
    }
}
