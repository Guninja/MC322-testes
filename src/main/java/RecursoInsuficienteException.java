/**
 * Exceção de domínio lançada quando a fábrica não possui recursos suficientes
 * (como saldo no orçamento ou estoque de matéria-prima) para concluir uma operação.
 * 
 * @author Luis Gustavo
 */
public class RecursoInsuficienteException extends RuntimeException {
    /**
     * Constrói a exceção com uma mensagem detalhando o recurso em falta.
     * 
     * @param mensagem Descrição do erro de falta de recurso ocorrido.
     */
    public RecursoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}