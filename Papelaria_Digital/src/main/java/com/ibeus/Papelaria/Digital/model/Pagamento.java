package com.ibeus.Papelaria.Digital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pagamentos")
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
    private Pedido pedido;

    public Pagamento(String tipoPagamento, String mensagem, Pedido pedido) {
        this.tipoPagamento = tipoPagamento;
        this.pedido = pedido;
    }

    public Pagamento() {
        // Construtor padrão necessário para JPA
    }
}
