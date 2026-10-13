/**
 * Exceção de domínio lançada quando ocorre uma operação inválida
 * associada ao ciclo de vida ou aos dados de uma Demanda.
 * 
 * @author Júlio Marcos
 */
public class DemandaInvalidaException extends RuntimeException {
    /**
     * Constrói a exceção com uma mensagem explicativa.
     * 
     * @param mensagem Descrição do erro ocorrido.
     */
    public DemandaInvalidaException(String mensagem) {
        super(mensagem);
    }
}