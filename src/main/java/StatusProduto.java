/**
 * Enumeração que representa os  estados de um produto na linha de produção da fábrica.
 * Utiliza tipagem forte evitando o uso de Strings soltas.
 * Produto em fila ou pronto?
 * @author Luis Gustavo
 */
public enum StatusProduto { 
    /**
     * Indica estado do produto aguardando ser processado/fabricado.
     */
    PARADO ("Parado"),
    /**
     * Indica que o produto já passou pelo processo de fabricação e esta no estoque.
     */
    PRODUZIDO ("Produzido");
    
    private final String estado;

    /**
     * Construtor interno da enumeração de status.
     * 
     * @param estado A palavra que representa o estado do produto.
     */
    StatusProduto (String estado){
        this.estado=estado;
    }

    /**
     * Recupera a descrição textual legível do status atual do produto.
     * 
     * @return Uma String contendo a ppalavra que representa o estado.
     */
    public String getDescricao(){
        return estado;
    }
}