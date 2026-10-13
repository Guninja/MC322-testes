import java.util.List;

/**
 * Estrategia de producao que escolhe a demanda com melhor custo-beneficio,
 * ou seja, a que produz a maior quantidade de produtos por valor gasto.
 * 
 * @author Júlio Marcos
 */
public class EstrategiaMaxProdutos implements EstrategiaProducao {

    /**
     * Seleciona a demanda pendente com melhor razao entre quantidade de produtos
     * e orcamento necessario, respeitando o limite financeiro disponivel.
     * 
     * @param demandas Lista de demandas disponiveis para selecao.
     * @param orcamentoDisponivel Valor do orcamento atual disponivel na fabrica.
     * @return A demanda com melhor custo-beneficio, ou null se nenhuma for viavel.
     * @throws IllegalArgumentException Se a lista de demandas for nula ou o orcamento for negativo.
     */
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        if (demandas == null) {
            throw new IllegalArgumentException("A lista de demandas nao pode ser nula.");
        }
        if (orcamentoDisponivel < 0) {
            throw new IllegalArgumentException("O orcamento disponivel nao pode ser negativo.");
        }

        Demanda aux = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda d = demandas.get(i);
            if (d != null && d.getStatus() == StatusDemanda.PENDENTE && d.getOrcamentoNecessario() <= orcamentoDisponivel) {
                // Garante que o orcamento nao seja zero para evitar divisao por zero
                if (d.getOrcamentoNecessario() > 0) {
                    if (aux == null || (d.getQuantidadeProdutos() / d.getOrcamentoNecessario() > aux.getQuantidadeProdutos() / aux.getOrcamentoNecessario())) {
                        aux = d;
                    }
                }
            } 
        }
        return aux; 
    }

    /**
     * Retorna o nome descritivo desta estrategia.
     * 
     * @return Nome da estrategia.
     */
    @Override 
    public String getNomeEstrategia() {
        return "Prioridade para o melhor custo beneficio";
    }
}