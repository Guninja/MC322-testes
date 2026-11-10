package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class DemandaTeste {

    @Test
    @DisplayName("Deve criar demanda válida com status inicial PENDENTE")
    void testeCriacaoDemanda() {
        Demanda demanda = new Demanda("D-01", 10);

        assertNotNull(demanda);
        assertEquals(10, demanda.getQuantidadeProdutos());
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());
    }

    @Test
    @DisplayName("Deve transitar corretamente entre os status da demanda")
    void testeTransicaoStatusDemanda() {
        Demanda demanda = new Demanda("D-02", 20);

        demanda.setStatus(StatusDemanda.EM_PRODUCAO);
        assertEquals(StatusDemanda.EM_PRODUCAO, demanda.getStatus());

        demanda.setStatus(StatusDemanda.CONCLUIDA);
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção para demanda com quantidade inválida")
    void testeDemandaQuantidadeInvalida() {
        assertThrows(DemandaInvalidaException.class, () -> {
            new Demanda("D-ERR", -5);
        });
    }
}