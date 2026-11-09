package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EstrategiaProducaoTeste {

    @Test
    @DisplayName("Deve selecionar demanda utilizando a estratégia Ordem de Chegada")
    void testeEstrategiaOrdemChegada() {
        EstrategiaProducao estrategia = new EstrategiaOrdemChegada();
        List<Demanda> demandas = new ArrayList<>();
        Demanda d1 = new Demanda("D1", 5);
        Demanda d2 = new Demanda("D2", 10);
        demandas.add(d1);
        demandas.add(d2);

        Demanda selecionada = estrategia.selecionarDemanda(demandas, 1000.0);
        assertNotNull(selecionada);
        assertEquals(d1, selecionada);
    }

    @Test
    @DisplayName("Deve selecionar demanda utilizando a estratégia Maior Demanda")
    void testeEstrategiaMaiorDemanda() {
        EstrategiaProducao estrategia = new EstrategiaMaiorDemanda();
        List<Demanda> demandas = new ArrayList<>();
        Demanda d1 = new Demanda("D1", 5);
        Demanda d2 = new Demanda("D2", 50);
        demandas.add(d1);
        demandas.add(d2);

        Demanda selecionada = estrategia.selecionarDemanda(demandas, 2000.0);
        assertNotNull(selecionada);
        assertEquals(d2, selecionada);
    }

    @Test
    @DisplayName("Deve tratar lista vazia retornando null na estratégia")
    void testeEstrategiaListaVazia() {
        EstrategiaProducao estrategia = new EstrategiaMaxProdutos();
        Demanda selecionada = estrategia.selecionarDemanda(new ArrayList<>(), 500.0);

        assertNull(selecionada);
    }
}