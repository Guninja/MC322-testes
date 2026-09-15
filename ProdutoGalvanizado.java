public class ProdutoGalvanizado extends Produto {
    public ProdutoGalvanizado(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.7f, 2);
    }

    public boolean processar(){
        setStatus("produzido");
        System.out.println("Produto produzido...");
        return true;
    }

    public int calcularTempoProducao(){
        return 18;//18 minutos para produzir lataria galvanizada
    }

    public String getTipo(){
        return "ProdutoGalvanizado";
    }
}