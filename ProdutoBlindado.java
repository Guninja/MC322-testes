public class ProdutoBlindado extends Produto {
    public ProdutoBlindado(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.9f);
    }

    public boolean processar(){
        setStatus("produzido");
        System.out.println("Produto produzido...");
        return true;
    }

    public int calcularTempoProducao(){
        return 30;//30minutos para produzie lataria blindada
    }

    public String getTipo(){
        return "ProdutoBlindado";
    }
}