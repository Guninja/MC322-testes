public enum StatusDemanda {
    PENDENTE ("Demandas em aberto, esperando produção"),
    EM_PRODUCAO ("Demandas sendo produzidas"),
    CONCLUIDA ("Demandas de já produzidas"),
    CANCELADA ("Demandas com produção cancelada, falta orçamento ou insumos");

    private final String estado;

    StatusDemanda (String estado){
        this.estado=estado;
    }

    public String getDescricao(){
        return estado;
    }
}