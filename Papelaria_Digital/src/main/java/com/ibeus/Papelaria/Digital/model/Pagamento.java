package com.ibeus.Papelaria.Digital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pagamentos")
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPagamento;

    private String mensagem;

    public Pagamento(String tipoPagamento, String mensagem) {
        this.tipoPagamento = tipoPagamento;
        this.mensagem = mensagem;
    }

    public Pagamento() {
        // Construtor padrão necessário para JPA
    }
}
