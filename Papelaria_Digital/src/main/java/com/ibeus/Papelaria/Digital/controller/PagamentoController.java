package com.ibeus.Papelaria.Digital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibeus.Papelaria.Digital.model.Pagamento;
import com.ibeus.Papelaria.Digital.service.PagamentoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Endpoint para listar as opções de pagamento
    @GetMapping("/opcoes")
    public ResponseEntity<List<String>> getOpcoesPagamento() {
        return ResponseEntity.ok(pagamentoService.getOpcoesPagamento());
    }

    // Endpoint para processar o pagamento
    @PostMapping("/finalizar")
    public ResponseEntity<Pagamento> processarPagamento(@RequestBody String tipoPagamento) {
        Pagamento pagamento = pagamentoService.processarPagamento(tipoPagamento);
        if (pagamento != null) {
            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.badRequest().body(new Pagamento(null, "Tipo de pagamento inválido."));
        }
    }
}
