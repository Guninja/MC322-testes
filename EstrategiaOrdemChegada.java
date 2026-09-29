import java.util.List;
public class EstrategiaOrdemChegada implements EstrategiaProducao{
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){// le a lista de demanda e faz com que as demandas a serem executadas estejam em ordem de chegada
        for(int i = 0; i < demandas.size(); i++){
            if (demandas.get(i).getStatus() == StatusDemanda.PENDENTE && demandas.get(i).getOrcamentoNecessario() <= orcamentoDisponivel) {//confere se a demanda esta pendente e se tem dinheiro
                return demandas.get(i);
            }
        } return null;

    }
    @Override 
    public String getNomeEstrategia(){
        return "Prioridade para quem chegou primeiro";
    }
    
}