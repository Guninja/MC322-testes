public class ProdutoChapaBruta extends Produto {
    public ProdutoChapaBruta(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.5f, 1);
    }

    public boolean processar(){
        setStatus("produzido");
        System.out.println("Produto produzido...");
        return true;
    }

    public int calcularTempoProducao(){
        return 12;//12 minutos para produzir lataria em chapa bruta
    }

    public String getTipo(){
        return "ProdutoChapaBruta";
    }
}