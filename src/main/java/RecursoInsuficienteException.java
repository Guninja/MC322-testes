public class RecursoInsuficienteException extends RuntimeException {//classe de erros para falta de recursos ao exercutar alguma ação
    public RecursoInsuficienteException(String mensagem){
        super(mensagem);
    }
}
