package br.unicamp.ic.mc322.exception;

/**
 * Exceção de domínio lançada quando ocorre uma falha durante a leitura,
 * escrita ou validação dos dados persistidos em arquivo XML.
 * 
 * @author Luis Gustavo
 */
public class PersistenciaException extends RuntimeException {

    /**
     * Constrói uma nova exceção de persistência com a mensagem descritiva do erro.
     * 
     * @param mensagem Explicação clara da falha ocorrida no arquivo XML.
     */
    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Constrói uma nova exceção de persistência encapsulando a causa original da falha.
     * 
     * @param mensagem Explicação clara da falha ocorrida no arquivo XML.
     * @param causa Exceção original capturada durante o processamento do arquivo.
     */
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}