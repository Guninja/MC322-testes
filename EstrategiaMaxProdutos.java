import java.util.List;
public class EstrategiaMaxProdutos implements EstrategiaProducao{
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){
        Demanda aux = null;
        for(int i = 0; i < demandas.size(); i++){
            if (demandas.get(i).getStatus() == StatusDemanda.PENDENTE && demandas.get(i).getOrcamentoNecessario() <= orcamentoDisponivel) {//confere se a demanda esta pendente e se tem dinheiro
                if (aux == null || demandas.get(i).getQuantidadeProdutos() / demandas.get(i).getOrcamentoNecessario() > aux.getQuantidadeProdutos() / aux.getOrcamentoNecessario()) {
                    aux = demandas.get(i);
                }
            } 
        }return aux; 
    }
    @Override 
    public String getNomeEstrategia(){
        return "Prioridade para o melhor custo beneficio";
    }
    
}