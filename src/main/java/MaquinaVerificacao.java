/**
 * Classe que representa uma maquina de verificacao e inspecao de qualidade na planta industrial.
 * Realiza os testes no produto e o descarta caso nao passe nos criterios de inspecao.
 * 
 * @author Júlio Marcos
 */
public class MaquinaVerificacao extends Maquina {

    /**
     * Construtor para inicializar a maquina de verificacao.
     * 
     * @param nomeMaq Nome identificador da maquina.
     * @param capMax Capacidade maxima de materia prima.
     * @param prob Probabilidade base de falha.
     * @param custoOpe Custo por operacao.
     * @param fatorDesgaste Fator de desgaste da saude.
     */
    public MaquinaVerificacao(String nomeMaq, int capMax, float prob, int custoOpe, int fatorDesgaste) {
        super(nomeMaq, capMax, prob, custoOpe, fatorDesgaste);
    }

    /**
     * Processa e inspeciona o produto utilizando a materia prima fornecida.
     * 
     * @param obraPrima Produto a ser verificado e processado.
     * @param material Materia prima a ser utilizada.
     * @return true se o produto passou na inspecao e foi processado, false caso contrario.
     * @throws IllegalArgumentException Se o produto ou a materia prima forem nulos.
     */
    @Override
    public boolean processar(Produto obraPrima, MateriaPrima material) {
        if (obraPrima == null) {
            throw new IllegalArgumentException("O produto a ser processado nao pode ser nulo.");
        }
        if (material == null) {
            throw new IllegalArgumentException("A materia prima nao pode ser nula.");
        }

        int demandaMaterial = obraPrima.getQuantidadeMateriaPrimaPorUnidade();
        if (!estaLigada()) {
            System.out.println("Produto não processado, poxa, a máquina está desligada!");
        } else if (getCapacidadeMaxima() < demandaMaterial) {
            System.out.println("Demanda de MP maior que a capacidade da Máquina, as vezes menos é mais...");
        } else if (material.consumir(demandaMaterial)) {
            desgate(); // aplica o desgate na maquina
            if (verificarFalha()) {
                System.out.println("Produto reprovado e descartado, não passou nos testes de inspeção");
                return false; 
            }
            if (Math.random() < obraPrima.getProbabilidadeFalha()) {
                System.out.println("Produto reprovado e descartado, não passou nos testes de inspeção");
                return false;
            }
            return obraPrima.processar();
        } else {
            System.out.println("Falta estoqueeee!!! Máquina não processou");
        }
        return false;
    }

    /**
     * Retorna o tipo especifico desta maquina.
     * 
     * @return Nome do tipo da maquina.
     */
    @Override
    public String getTipo() {
        return "Maquina de Verificação";
    }
}