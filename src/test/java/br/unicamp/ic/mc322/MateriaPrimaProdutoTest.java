package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;
import br.unicamp.ic.mc322.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para as classes MateriaPrima, hierarquia de Produto e Cenario.
 * Avalia regras de consumo de estoque, casos-limite, cálculo de risco e lançamento de exceções.
 * 
 * @author Luis Gustavo
 */
class MateriaPrimaProdutoTest {

    @Test
    void consumirQuantidadeMenorQueEstoqueAtualizaSaldoCorretamente() {
        MateriaPrima metal = new MateriaPrima("MP1", "Metal", "m^2", 10, 5);
        assertTrue(metal.consumir(3));
        assertEquals(7, metal.getQuantidade());
    }

    @Test
    void consumirQuantidadeIgualAoEstoqueZeraSaldo() {
        MateriaPrima metal = new MateriaPrima("MP1", "Metal", "m^2", 10, 5);
        assertTrue(metal.consumir(10));
        assertEquals(0, metal.getQuantidade());
    }

    @Test
    void consumirQuantidadeMaiorQueEstoqueLancaRecursoInsuficienteException() {
        MateriaPrima metal = new MateriaPrima("MP1", "Metal", "m^2", 5, 5);
        assertThrows(RecursoInsuficienteException.class, () -> metal.consumir(6));
        assertEquals(5, metal.getQuantidade());
    }

    @Test
    void consumirOuAdicionarValorNaoPositivoLancaIllegalArgumentException() {
        MateriaPrima metal = new MateriaPrima("MP1", "Metal", "m^2", 10, 5);
        assertThrows(IllegalArgumentException.class, () -> metal.consumir(0));
        assertThrows(IllegalArgumentException.class, () -> metal.consumir(-2));
        assertThrows(IllegalArgumentException.class, () -> metal.adicionarEstoque(0));
        assertThrows(IllegalArgumentException.class, () -> metal.adicionarEstoque(-5));
    }

    @Test
    void subclassesDeProdutoPossuemAtributosETemposCorretos() {
        Produto blindado = new ProdutoBlindado("P1", "Lataria Blindada");
        Produto galvanizado = new ProdutoGalvanizado("P2", "Lataria Galvanizada");
        Produto chapa = new ProdutoChapaBruta("P3", "Chapa Bruta");

        assertEquals(4, blindado.getQuantidadeMateriaPrimaPorUnidade());
        assertEquals(30, blindado.calcularTempoProducao());
        assertEquals(StatusProduto.PARADO, blindado.getStatus());

        assertEquals(2, galvanizado.getQuantidadeMateriaPrimaPorUnidade());
        assertEquals(18, galvanizado.calcularTempoProducao());

        assertEquals(1, chapa.getQuantidadeMateriaPrimaPorUnidade());
        assertEquals(12, chapa.calcularTempoProducao());

        assertTrue(blindado.processar());
        assertEquals(StatusProduto.PRODUZIDO, blindado.getStatus());
    }

    @Test
    void produtoAvaliaNecessidadeDeManutencaoQuandoRiscoUltrapassaLimite() {
        ProdutoChapaBruta chapa = new ProdutoChapaBruta("P1", "Chapa");
        // Risco inicial = 0.5 * 0.30 = 0.15 (<= 0.30, não precisa de manutenção)
        assertFalse(chapa.precisaManutencao());

        chapa.aumentarProbabilidadeFalha(0.20f);
        // Risco acumulado = 0.35 (> 0.30, precisa de manutenção)
        assertTrue(chapa.precisaManutencao());
        assertTrue(chapa.gerarRelatorioDiagnostico().contains("precisa de manutenção"));
    }

    @Test
    void aumentarProbabilidadeFalhaComValorNegativoLancaExcecao() {
        ProdutoBlindado blindado = new ProdutoBlindado("P1", "Blindado");
        assertThrows(IllegalArgumentException.class, () -> blindado.aumentarProbabilidadeFalha(-0.1f));
    }

    @Test
    void cenariosPossuemConfiguracoesValidas() {
        assertEquals(10000, Cenario.IDEAL.getBudgetInicial());
        assertEquals(0.05f, Cenario.IDEAL.getFatorFalha(), 0.001f);
        assertEquals(1, Cenario.IDEAL.getFatorDesgaste());

        assertEquals(300, Cenario.APOCALIPTICO.getBudgetInicial());
        assertEquals(0.40f, Cenario.APOCALIPTICO.getFatorFalha(), 0.001f);
        assertEquals(6, Cenario.APOCALIPTICO.getFatorDesgaste());
    }
}