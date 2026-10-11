public class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida = false;

    public Demanda(String tipoMetal, int quantosProdutos){
        tipoProduto = tipoMetal;
        quantidadeProdutos = quantosProdutos;
    }

    public int atualizarDemanda(int demanda){
        quantidadeProdutos = quantidadeProdutos + demanda;
        return quantidadeProdutos;
    }

    public int calcularMPnecessaria(int quantidadeMPporunidade){
        int MPnecessaria = quantidadeMPporunidade * quantidadeProdutos;
        return MPnecessaria;
    }

    public boolean atender(){
        if (quantidadeProdutos == 0) {
            atendida = true;
            System.out.println("Demanda atendida!!!");
        }
        return atendida;
    }

    public String getTipoProduto(){
        return tipoProduto;
    }

    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }
}