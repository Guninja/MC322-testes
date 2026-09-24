public class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private StatusDemanda status;

    public Demanda(String tipoMetal, int quantosProdutos){
        tipoProduto = tipoMetal;
        quantidadeProdutos = quantosProdutos;
        status = StatusDemanda.PENDENTE;
    }

    public int atualizarDemanda(int demanda){
        ProducaoIniciada();
        quantidadeProdutos = quantidadeProdutos + demanda;
        if(quantidadeProdutos == 0){
            ProducaoFinalizada();
        }
        return quantidadeProdutos;
    }

    public int calcularMPnecessaria(int quantidadeMPporunidade ){
        int MPnecessaria = quantidadeMPporunidade * quantidadeProdutos;
        return MPnecessaria;
    }

    public String ProducaoIniciada(){
        if(status == StatusDemanda.PENDENTE){
            status = StatusDemanda.EM_PRODUCAO;
            return "Em produção...";
        } else{
            return "Não tem como começar essa demanda";
        }       
    }
    public String ProducaoFinalizada(){
        if(status == StatusDemanda.EM_PRODUCAO){
            status = StatusDemanda.CONCLUIDA;
            return "Demanda finalizada :)";
        } else{
            return "Calma, essa demanda nao está sendo produzida";
        }       
    }
    public String ProducaoCancelada(){
            if(status != StatusDemanda.CONCLUIDA){
                status = StatusDemanda.CANCELADA;
                return "Que pena. Essa demanda está cancelada :(";
            } else{
                return "Tarde demais, ja esta finalizada";
            }
    }
    public StatusDemanda getStatus(){
        return status;
    }

    public float getOrcamentoNecessario(float custoMaquinas){
        return quantidadeProdutos * custoMaquinas;
    }

    public  String getTipoProduto(){
        return tipoProduto;
    }
    
    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }
}