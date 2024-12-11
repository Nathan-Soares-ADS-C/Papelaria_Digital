package com.ibeus.Papelaria.Digital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibeus.Papelaria.Digital.model.Pagamento;
import com.ibeus.Papelaria.Digital.model.Pedido;
import com.ibeus.Papelaria.Digital.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoService pagamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    // Teste para listar todos os pagamentos
    @Test
    @WithMockUser // Simula um usuário autenticado (com role padrão)
    void getAllPagamentos_ShouldReturnListOfPagamentos() throws Exception {
        Pedido pedidoMock = new Pedido(); // Mock ou instância válida
        Pagamento pagamento1 = new Pagamento("Cartão", "Teste", pedidoMock);
        Pagamento pagamento2 = new Pagamento("Dinheiro", "Teste 2", pedidoMock);

        when(pagamentoService.getAllPagamentos()).thenReturn(Arrays.asList(pagamento1, pagamento2));

        mockMvc.perform(get("/pagamentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tipoPagamento").value("Cartão"))
                .andExpect(jsonPath("$[1].tipoPagamento").value("Dinheiro"));
    }

    // Teste para obter um pagamento por ID
    @Test
    @WithMockUser // Simula um usuário autenticado
    void getPagamentoById_ShouldReturnPagamento() throws Exception {
        Pedido pedidoMock = new Pedido(); // Mock ou instância válida
        Pagamento pagamento = new Pagamento("Cartão", "Teste", pedidoMock);

        when(pagamentoService.getPagamentoById(1L)).thenReturn(pagamento);

        mockMvc.perform(get("/pagamentos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tipoPagamento").value("Cartão"));
    }

    // Teste para criar um pagamento
    @Test
    @WithMockUser // Simula um usuário autenticado
    void createPagamento_ShouldCreatePagamento() throws Exception {
        Pedido pedidoMock = new Pedido(); // Mock ou instância válida
        Pagamento pagamento = new Pagamento("Boleto", "Teste", pedidoMock);

        when(pagamentoService.savePagamento(Mockito.any(Pagamento.class))).thenReturn(pagamento);

        mockMvc.perform(post("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagamento)))
                .andExpect(status().isOk())  // Ajuste o status esperado para `200 OK`
                .andExpect(jsonPath("$.tipoPagamento").value("Boleto"));
    }

   
    @Test
    @WithMockUser // Simula um usuário autenticado
    void updatePagamento_ShouldUpdatePagamento() throws Exception {
        Pedido pedidoMock = new Pedido(); // Mock ou instância válida
        Pagamento pagamento = new Pagamento("Transferência", "Teste", pedidoMock);

        when(pagamentoService.updatePagamento(eq(1L), Mockito.any(Pagamento.class))).thenReturn(pagamento);

        mockMvc.perform(put("/pagamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoPagamento").value("Transferência"));
    }

    // Teste para excluir um pagamento
    @Test
    @WithMockUser // Simula um usuário autenticado
    void deletePagamento_ShouldDeletePagamento() throws Exception {
        doNothing().when(pagamentoService).deletePagamento(1L);

        mockMvc.perform(delete("/pagamentos/1"))
                .andExpect(status().isOk());
    }
}
