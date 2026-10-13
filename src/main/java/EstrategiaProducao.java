import java.util.List;

/**
 * Interface que define como as estrategias de selecao de demanda devem funcionar.
 * As classes que implementarem essa interface vao decidir qual demanda produzir primeiro.
 * 
 * @author Júlio Marcos
 */
public interface EstrategiaProducao {

    /**
     * Escolhe qual demanda da lista deve ser produzida com base no orcamento disponivel.
     * 
     * @param demandas Lista com as demandas disponiveis.
     * @param orcamentoDisponivel Quanto dinheiro a fabrica tem para gastar.
     * @return A demanda escolhida para ser produzida, ou null se nenhuma for viavel.
     * @throws IllegalArgumentException Se a lista de demandas for nula ou o orcamento for negativo.
     */
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    /**
     * Retorna o nome da estrategia que esta sendo usada.
     * 
     * @return Nome da estrategia em texto.
     */
    String getNomeEstrategia();
}