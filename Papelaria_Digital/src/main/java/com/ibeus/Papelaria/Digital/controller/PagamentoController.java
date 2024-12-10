package com.ibeus.Papelaria.Digital.controller;

import com.ibeus.Papelaria.Digital.model.Pagamento;
import com.ibeus.Papelaria.Digital.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public List<Pagamento> getAllPagamentos() {
        return pagamentoService.getAllPagamentos();
    }

    @GetMapping("/{id}")
    public Pagamento getPagamentoById(@PathVariable long id) {
        return pagamentoService.getPagamentoById(id);
    }

    @PostMapping
    public Pagamento createPagamento(@RequestBody Pagamento pagamento) {
        return pagamentoService.savePagamento(pagamento);
    }

    @PutMapping("/{id}")
    public Pagamento updatePagamento(@PathVariable long id, @RequestBody Pagamento pagamento) {
        return pagamentoService.updatePagamento(id, pagamento);
    }

    @DeleteMapping("/{id}")
    public void deletePagamento(@PathVariable long id) {
        pagamentoService.deletePagamento(id);
    }
}
