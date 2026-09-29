import java.util.List;
public class EStrategiaMaiorDemanda implements EstrategiaProducao{
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){// le  a lista de demanda e faz com que a demanda com mais itens a ser produzidos seja atendida primeiro
        Demanda aux = null;
        for(int i = 0; i < demandas.size(); i++){
            if (demandas.get(i).getStatus() == StatusDemanda.PENDENTE && demandas.get(i).getOrcamentoNecessario() <= orcamentoDisponivel) {//confere se a demanda esta pendente e se tem dinheiro
                if (aux == null || demandas.get(i).getQuantidadeProdutos() > aux.getQuantidadeProdutos()) {
                    aux = demandas.get(i);
                }
            } 
        }return aux;
    }
    @Override 
    public String getNomeEstrategia(){
        return "Vamos produzir ao Máximo!!!";
    } 
}