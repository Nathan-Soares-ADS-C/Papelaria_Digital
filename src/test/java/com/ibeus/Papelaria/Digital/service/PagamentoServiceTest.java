package com.ibeus.Papelaria.Digital.service;

import com.ibeus.Papelaria.Digital.model.Pagamento;
import com.ibeus.Papelaria.Digital.model.Pedido;
import com.ibeus.Papelaria.Digital.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Pagamento pagamento;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamento = new Pagamento();
        pagamento.setId(1L);
        pedido = new Pedido();
        pedido.setId(1L);
        pagamento.setPedido(pedido);
    }

    @Test
    void testGetAllPagamentos() {
        List<Pagamento> pagamentos = List.of(pagamento, new Pagamento());
        when(pagamentoRepository.findAll()).thenReturn(pagamentos);

        List<Pagamento> result = pagamentoService.getAllPagamentos();
        assertThat(result).hasSize(2);
        verify(pagamentoRepository, times(1)).findAll();
    }

    @Test
    void testGetPagamentoById_Success() {
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(pagamento));
        Pagamento result = pagamentoService.getPagamentoById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void testGetPagamentoById_NotFound() {
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.empty());
        Pagamento result = pagamentoService.getPagamentoById(1L);
        assertThat(result).isNull();
    }

    @Test
    void testSavePagamento() {
        when(pedidoService.updatePedido(pedido)).thenReturn(pedido);
        when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);
        Pagamento result = pagamentoService.savePagamento(pagamento);
        assertThat(result).isNotNull();
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    void testUpdatePagamento_Success() {
        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);
        Pagamento result = pagamentoService.updatePagamento(1L, pagamento);
        assertThat(result).isNotNull();
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    void testUpdatePagamento_NotFound() {
        when(pagamentoRepository.existsById(1L)).thenReturn(false);
        Pagamento result = pagamentoService.updatePagamento(1L, pagamento);
        assertThat(result).isNull();
    }

    @Test
    void testDeletePagamento_Success() {
        when(pagamentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pagamentoRepository).deleteById(1L);
        pagamentoService.deletePagamento(1L);
        verify(pagamentoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePagamento_NotFound() {
        when(pagamentoRepository.existsById(1L)).thenReturn(false);
        pagamentoService.deletePagamento(1L);
        verify(pagamentoRepository, never()).deleteById(1L);
    }
}