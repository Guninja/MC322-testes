/**
 * Enumeração que representa os diferentes estados que uma {@link Demanda}
 * pode assumir ao longo do seu ciclo de vida na planta industrial.
 * 
 * @author Júlio Marcos
 */
public enum StatusDemanda {
    /**
     * Indica que a demanda foi criada e está aguardando início da produção.
     */
    PENDENTE("Demandas em aberto, esperando produção"),

    /**
     * Indica que a demanda está atualmente em processo de fabricação.
     */
    EM_PRODUCAO("Demandas sendo produzidas"),

    /**
     * Indica que a produção do lote de produtos foi finalizada com sucesso.
     */
    CONCLUIDA("Demandas de já produzidas"),

    /**
     * Indica que a produção foi interrompida ou cancelada devido a insumos/orçamento insuficientes.
     */
    CANCELADA("Demandas com produção cancelada, falta orçamento ou insumos");

    private final String estado;

    /**
     * Construtor privado da enumeração StatusDemanda.
     * 
     * @param estado Descrição textual associada ao estado da demanda.
     */
    StatusDemanda(String estado) {
        this.estado = estado;
    }

    /**
     * Obtém a descrição textual detalhada sobre o estado da demanda.
     * 
     * @return String contendo a explicação do status atual.
     */
    public String getDescricao() {
        return estado;
    }
}