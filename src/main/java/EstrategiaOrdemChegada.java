import java.util.List;

/**
 * Estrategia de producao que seleciona a primeira demanda pendente da lista
 * que couber no orcamento disponivel, respeitando a ordem de chegada (FIFO).
 * 
 * @author Júlio Marcos
 */
public class EstrategiaOrdemChegada implements EstrategiaProducao {

    /**
     * Percorre a lista de demandas na ordem em que foram cadastradas e retorna a primeira
     * que estiver com status PENDENTE e cujo orcamento necessario seja compativel.
     * 
     * @param demandas Lista de demandas cadastradas na planta.
     * @param orcamentoDisponivel Valor do orcamento atualmente disponivel.
     * @return A primeira demanda pendente que cabe no orcamento, ou null se nenhuma for atenda.
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

        for (int i = 0; i < demandas.size(); i++) {
            Demanda d = demandas.get(i);
            if (d != null && d.getStatus() == StatusDemanda.PENDENTE && d.getOrcamentoNecessario() <= orcamentoDisponivel) {
                return d;
            }
        } 
        return null;
    }

    /**
     * Retorna o nome da estrategia de ordem de chegada.
     * 
     * @return Nome descritivo da estrategia.
     */
    @Override 
    public String getNomeEstrategia() {
        return "Prioridade para quem chegou primeiro";
    }
}