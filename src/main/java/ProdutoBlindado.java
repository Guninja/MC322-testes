public class ProdutoBlindado extends Produto {
    public ProdutoBlindado(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.9f, 4);
    }

    public boolean processar(){
        setStatus(StatusProduto.PRODUZIDO);
        System.out.println("Produto produzido...");
        return true;
    }

    public int calcularTempoProducao(){
        return 30;//30 minutos para produzir lataria blindada
    }

    public String getTipo(){
        return "ProdutoBlindado";
    }
}