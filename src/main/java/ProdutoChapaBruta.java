/**
 * Subclasse que representa uma peça de lataria de baixa qualidade (Chapa Bruta) na fábrica.
 * Exige o menor consumo de matéria-prima e possui o tempo de processamento mais rápido.
 * 
 * @author Luis Gustavo
 */
public class ProdutoChapaBruta extends Produto {
    /**
     * Construtor da classe ProdutoChapaBruta.
     * Inicializa a peça transferindo uma qualidade fixa de 0.5 e custo de 1 unidade para a superclasse.
     * 
     * @param identificacao O identificador único do produto (ID do lote).
     * @param nomezinho O nome descritivo da peça.
     */
    public ProdutoChapaBruta(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.5f, 1);
    }

    /**
     * Executa o processamento específico da lataria de chapa bruta na linha de montagem.
     * 
     * @return true confirmando que a peça foi processada e finalizada com sucesso.
     */
    public boolean processar(){
        setStatus(StatusProduto.PRODUZIDO);
        System.out.println("Produto produzido...");
        return true;
    }

    /**
     * Calcula o tempo estimado exigido pelas máquinas para fabricar esta peça.
     * 
     * @return Um valor inteiro representando o tempo de produção (12 minutos).
     */
    public int calcularTempoProducao(){
        return 12;
    }

    /**
     * Recupera a categoria ou tipo da peça fabricada.
     * 
     * @return Uma String indicando que é um "ProdutoChapaBruta".
     */
    public String getTipo(){
        return "ProdutoChapaBruta";
    }
}