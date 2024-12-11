package com.ibeus.Papelaria.Digital.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ibeus.Papelaria.Digital.model.Pedido;
import com.ibeus.Papelaria.Digital.service.PedidoService;

class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus("PENDING");
    }

    @Test
    void testListarTodos_Success() throws Exception {
        List<Pedido> pedidos = List.of(pedido, new Pedido());
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        mockMvc.perform(get("/pedidos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(pedidos.size()));
    }

    @Test
    void testBuscarPorId_Success() throws Exception {
        when(pedidoService.buscarPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/pedidos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testBuscarPorId_NotFound() throws Exception {
        when(pedidoService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testSalvar_Success() throws Exception {
        when(pedidoService.salvar(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"PENDING\"}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testAtualizarStatus_Success() throws Exception {
        when(pedidoService.atualizarStatus(eq(1L), anyString())).thenReturn(pedido);

        mockMvc.perform(put("/pedidos/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("CONFIRMED"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testAtualizarStatus_NotFound() throws Exception {
        when(pedidoService.atualizarStatus(eq(1L), anyString())).thenReturn(null);

        mockMvc.perform(put("/pedidos/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("CONFIRMED"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testRemoverPedido_Success() throws Exception {
        doNothing().when(pedidoService).removerPedido(1L);

        mockMvc.perform(delete("/pedidos/1"))
               .andExpect(status().isOk())
               .andExpect(content().string("Pedido removido com sucesso!"));
    }

    @Test
    void testObterStatus_Success() throws Exception {
        when(pedidoService.buscarPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/pedidos/1/status"))
               .andExpect(status().isOk())
               .andExpect(content().string("PENDING"));
    }

    @Test
    void testObterStatus_NotFound() throws Exception {
        when(pedidoService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/1/status"))
               .andExpect(status().isOk())
               .andExpect(content().string("Pedido não encontrado"));
    }
}
