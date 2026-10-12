import java.util.List;

/**
 * Estrategia de producao que escolhe a demanda com a maior quantidade de produtos
 * entre as que estao pendentes e cabem no orcamento disponivel.
 * 
 * @author Júlio Marcos
 */
public class EstrategiaMaiorDemanda implements EstrategiaProducao {

    /**
     * Percorre a lista de demandas e seleciona a demanda pendente com mais itens a serem produzidos,
     * respeitando o limite do orcamento atual da fabrica.
     * 
     * @param demandas Lista com as demandas para analise.
     * @param orcamentoDisponivel Valor em dinheiro disponivel para realizar a producao.
     * @return A demanda pendente com maior quantidade de produtos, ou null se nenhuma couber no orcamento.
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
                if (aux == null || d.getQuantidadeProdutos() > aux.getQuantidadeProdutos()) {
                    aux = d;
                }
            } 
        }
        return aux;
    }

    /**
     * Retorna o identificador/frase desta estrategia.
     * 
     * @return Nome da estrategia.
     */
    @Override 
    public String getNomeEstrategia() {
        return "Vamos produzir ao Máximo!!!";
    } 
}