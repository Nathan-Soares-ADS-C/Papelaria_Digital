package com.ibeus.Papelaria.Digital.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibeus.Papelaria.Digital.model.Pagamento;

@Service
public class PagamentoService {

    public List<String> getOpcoesPagamento() {
        return Arrays.asList("Cartão de Crédito", "Cartão de Débito", "Pix"); //eu prevejo o nathan me mandando colocar dinheiro dnv aqui
    }

    public Pagamento processarPagamento(String tipoPagamento) {
        List<String> opcoesValidas = getOpcoesPagamento();
        if (opcoesValidas.contains(tipoPagamento)) {
            return new Pagamento(tipoPagamento, "Pagamento finalizado com sucesso.");
        } else {
            return null;
        }
    }
}
