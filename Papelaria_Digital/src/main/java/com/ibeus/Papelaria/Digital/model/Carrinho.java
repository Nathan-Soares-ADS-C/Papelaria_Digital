package com.ibeus.Papelaria.Digital.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "carrinho")
@Data
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    // Relacionamento com a entidade Produto (anteriormente Prato)
    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false) // Alterado para id_produto
    private Produto produto; // Alterado de prato para produto

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private Cliente cliente;
}
