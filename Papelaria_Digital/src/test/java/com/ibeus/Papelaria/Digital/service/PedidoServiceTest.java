package com.ibeus.Papelaria.Digital.service;

import com.ibeus.Papelaria.Digital.model.Pedido;
import com.ibeus.Papelaria.Digital.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;  

    @InjectMocks
    private PedidoService pedidoService; 

    private Pedido pedido;  

    @BeforeEach
    public void setUp() {
        
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus("Pendente");
    }

    @Test
    public void listarTodos() {
        
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));

   
        List<Pedido> pedidos = pedidoService.listarTodos();

       
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        verify(pedidoRepository, times(1)).findAll(); // Verifica se o método foi chamado uma vez
    }

    @Test
    public void buscarPorId() {
      
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        
        Optional<Pedido> pedidoBuscado = pedidoService.buscarPorId(1L);

   
        assertTrue(pedidoBuscado.isPresent());
        assertEquals("Pendente", pedidoBuscado.get().getStatus());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    public void salvar() {
       
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        
        Pedido pedidoSalvo = pedidoService.salvar(pedido);

       
        assertNotNull(pedidoSalvo);
        assertEquals("Pendente", pedidoSalvo.getStatus());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void updatePedido() {
      
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

      
        Pedido pedidoAtualizado = pedidoService.updatePedido(pedido);

    
        assertNotNull(pedidoAtualizado);
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void updatePedidoNaoEncontrado() {
        
        when(pedidoRepository.existsById(1L)).thenReturn(false);

       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.updatePedido(pedido);
        });

        
        assertEquals("Pedido não encontrado com o ID: 1", exception.getMessage());
        verify(pedidoRepository, times(1)).existsById(1L);
    }

    @Test
    public void atualizarStatus() {
    
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido pedidoComStatusAtualizado = pedidoService.atualizarStatus(1L, "Concluído");

        assertNotNull(pedidoComStatusAtualizado);
        assertEquals("Concluído", pedidoComStatusAtualizado.getStatus());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void atualizarStatusPedidoNaoEncontrado() {
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

    
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.atualizarStatus(1L, "Concluído");
        });

        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    public void removerPedido() {
        
        when(pedidoRepository.existsById(1L)).thenReturn(true);

       
        pedidoService.removerPedido(1L);

       
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void removerPedidoNaoEncontrado() {
       
        when(pedidoRepository.existsById(1L)).thenReturn(false);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.removerPedido(1L);
        });

        
        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
        verify(pedidoRepository, times(1)).existsById(1L);
    }
}
