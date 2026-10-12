/**
 * Subclasse que representa uma peça de lataria de média qualidade (Galvanizada) na fábrica.
 * Exige um consumo intermediário de matéria-prima e possui tempo de processamento mediano.
 * 
 * @author Luis Gustavo
 */
public class ProdutoGalvanizado extends Produto {
    /**
     * Construtor da classe ProdutoGalvanizado.
     * Inicializa a peça transferindo uma qualidade fixa de 0.7 e custo de 2 unidades para a superclasse.
     * 
     * @param identificacao O identificador único do produto (ID do lote).
     * @param nomezinho O nome descritivo da peça.
     */
    public ProdutoGalvanizado(String identificacao, String nomezinho){
        super(identificacao,nomezinho, 0.7f, 2);
    }

    /**
     * Executa o processamento específico da lataria galvanizada na linha de montagem.
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
     * @return Um valor inteiro representando o tempo de produção (18 minutos).
     */
    public int calcularTempoProducao(){
        return 18;//
    }

    /**
     * Recupera a categoria ou tipo da peça fabricada.
     * 
     * @return Uma String indicando que é um "ProdutoGalvanizado".
     */
    public String getTipo(){
        return "ProdutoGalvanizado";
    }
}