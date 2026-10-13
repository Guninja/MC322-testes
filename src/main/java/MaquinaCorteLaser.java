/**
 * Classe que representa uma maquina de corte a laser na planta industrial.
 * Realiza o processamento cortando os produtos e sofrendo desgaste a cada uso.
 * 
 * @author Júlio Marcos
 */
public class MaquinaCorteLaser extends Maquina {

    /**
     * Construtor para inicializar a maquina de corte a laser.
     * 
     * @param nomeMaq Nome identificador da maquina.
     * @param capMax Capacidade maxima de materia prima.
     * @param prob Probabilidade base de falha.
     * @param custoOpe Custo por operacao.
     * @param fatorDesgaste Fator de desgaste da saude.
     */
    public MaquinaCorteLaser(String nomeMaq, int capMax, float prob, int custoOpe, int fatorDesgaste) {
        super(nomeMaq, capMax, prob, custoOpe, fatorDesgaste);
    }

    /**
     * Processa o produto utilizando a materia prima informada.
     * 
     * @param obraPrima Produto que sera processado.
     * @param material Materia prima a ser consumida.
     * @return true se o produto foi processado com sucesso, false caso contrario.
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
            desgate(); // aplicar o desgate na maquina
            if (verificarFalha()) {
                obraPrima.aumentarProbabilidadeFalha(0.06f); 
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
     * @return String com o tipo da maquina.
     */
    @Override
    public String getTipo() {
        return "Maquina de corte a Laser";
    }
}