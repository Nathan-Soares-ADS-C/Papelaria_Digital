package com.ibeus.Papelaria.Digital.repository;

import com.ibeus.Papelaria.Digital.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void testSaveAndFindById() {
        // Criar um pedido para salvar
        Pedido pedido = new Pedido();
        pedido.setStatus("PENDING");
        pedidoRepository.save(pedido);

        // Buscar o pedido salvo
        Pedido foundPedido = pedidoRepository.findById(pedido.getId()).orElse(null);

        // Verificar se o pedido foi encontrado e se o status está correto
        assertThat(foundPedido).isNotNull();
        assertThat(foundPedido.getStatus()).isEqualTo(pedido.getStatus());
    }
}
