package com.ibeus.Papelaria.Digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Pagamento;
import com.ibeus.Papelaria.Digital.model.Pedido;
import com.ibeus.Papelaria.Digital.repository.PagamentoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoService pedidoService;  
    public List<Pagamento> getAllPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento getPagamentoById(long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    public Pagamento savePagamento(Pagamento pagamento) {
        if (pagamento.getPedido() != null) {
            Pedido pedido = pagamento.getPedido();
            
            pedido.setStatus("Pagamento Realizado");
            pedidoService.updatePedido(pedido);  
        }

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento updatePagamento(long id, Pagamento pagamento) {
        if (pagamentoRepository.existsById(id)) {
            pagamento.setId(id);
            return pagamentoRepository.save(pagamento);
        }
        return null;
    }

    public void deletePagamento(long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
        }
    }
}
