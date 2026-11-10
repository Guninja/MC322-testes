package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;
import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaOrdemChegada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe GerenciadorProducao.
 * Verifica compra de insumos, execução de ciclos de fabricação, integração com estratégias
 * e consistência do estado diante de falhas de recursos.
 * 
 * @author Luis Gustavo
 */
class GerenciadorProducaoTest {

    private MateriaPrima metal;
    private LinhaDeProducao linha;
    private GerenciadorProducao gerente;

    @BeforeEach
    void prepararFabrica() {
        metal = new MateriaPrima("MP1", "Metal", "m^2", 20, 5);
        linha = new LinhaDeProducao("Linha Teste");
        // Máquinas configuradas com probabilidade de falha 0.0 e desgaste 0 para execução determinística
        linha.adicionarMaquina(new MaquinaPrensaEstampagem("Prensa", 20, 0.0f, 2, 0));
        linha.adicionarMaquina(new MaquinaCorteLaser("Laser", 20, 0.0f, 3, 0));
        gerente = new GerenciadorProducao(100, metal, linha);
        gerente.setEstrategia(new EstrategiaOrdemChegada());
    }

    @Test
    void comprarMateriaPrimaComSaldoSuficienteDebitaBudgetEAdicionaEstoque() {
        gerente.comprarMateriaPrima(10, 5);
        assertEquals(50, gerente.getBudget());
        assertEquals(30, metal.getQuantidade());
    }

    @Test
    void comprarMateriaPrimaSemSaldoLancaExcecaoEMantemEstadoIntacto() {
        assertThrows(RecursoInsuficienteException.class, () -> gerente.comprarMateriaPrima(30, 5));
        assertEquals(100, gerente.getBudget());
        assertEquals(20, metal.getQuantidade());
    }

    @Test
    void executarProximaProducaoConcluiCicloEArmazenaProduto() {
        Demanda demanda = new Demanda("ProdutoChapaBruta", 1);
        gerente.registrarDemanda(demanda);

        boolean concluido = gerente.executarProximaProducao();

        assertTrue(concluido);
        // Custo de operação das 2 máquinas = 2 + 3 = 5 Robux (100 - 5 = 95)
        assertEquals(95, gerente.getBudget());
        // Consumo de MP = 1 unidade * 2 máquinas = 2 unidades (20 - 2 = 18)
        assertEquals(18, metal.getQuantidade());
        assertEquals(1, gerente.getProdutosFabricados().size());
        // Demanda de 1 unidade foi zerada e removida da fila
        assertTrue(gerente.getDemandas().isEmpty());
    }

    @Test
    void executarProximaProducaoSemEstoqueNaoDescontaBudget() {
        MateriaPrima mpZerada = new MateriaPrima("MP2", "Metal", "m^2", 0, 5);
        GerenciadorProducao gerenteSemMP = new GerenciadorProducao(100, mpZerada, linha);
        gerenteSemMP.setEstrategia(new EstrategiaOrdemChegada());
        gerenteSemMP.registrarDemanda(new Demanda("ProdutoChapaBruta", 1));

        assertThrows(RecursoInsuficienteException.class, () -> gerenteSemMP.executarProximaProducao());
        assertEquals(100, gerenteSemMP.getBudget());
        assertTrue(gerenteSemMP.getProdutosFabricados().isEmpty());
    }

    @Test
    void executarProximaProducaoSemEstrategiaOuSemDemandaViavelLancaIllegalStateException() {
        GerenciadorProducao gerenteSemEstrategia = new GerenciadorProducao(100, metal, linha);
        assertThrows(IllegalStateException.class, () -> gerenteSemEstrategia.executarProximaProducao());

        // Com estratégia definida, mas fila de demandas vazia
        assertThrows(IllegalStateException.class, () -> gerente.executarProximaProducao());
    }

    @Test
    void repararMaquinasRestauraSaudeDeTodaALinha() {
        linha.getMaquinas().get(0).restaurarEstado(10.0f, true);
        assertTrue(linha.precisaManutencao());

        gerente.repararMaquinas();

        assertFalse(linha.precisaManutencao());
        assertEquals(100.0f, linha.getMaquinas().get(0).getSaude(), 0.001f);
    }
}