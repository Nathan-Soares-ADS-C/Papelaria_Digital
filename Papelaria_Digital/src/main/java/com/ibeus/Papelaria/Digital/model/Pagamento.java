package com.ibeus.Papelaria.Digital.model;

import lombok.Data;

@Data
public class Pagamento {
    private String tipoPagamento;
    private String mensagem;

    public Pagamento(String tipoPagamento, String mensagem) {
        this.tipoPagamento = tipoPagamento;
        this.mensagem = mensagem;
    }
}
