/**
 * Interface que define o contrato genérico para os componentes auditáveis da fábrica(produtos/maquinas).
 * Classes que implementam esta interface comprometem-se a fornecer mecanismos 
 * de verificação de estado e relatórios de diagnóstico.
 * bem simples, só pra funcionar nas maquinas e produtos
 * 
 * @author Luis Gustavo
 */
public interface Auditavel { 
    /**
     * Gera um relatório descritivo diagnosticando o componente.
     * 
     * @return Uma String contendo as informações de status, risco e integridade do componente.
     */
    public String gerarRelatorioDiagnostico();
    /**
     * Avalia o estado do componente determinando se precisa de intervenção técnica.
     * 
     * @return true se precisa de manutenção, false caso contrário.
     */
    public boolean precisaManutencao();
}